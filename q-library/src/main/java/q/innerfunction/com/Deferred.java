package q.innerfunction.com;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple deferred promise implementation.
 * @author juliangoacher
 *
 * @param <T>
 */
public class Deferred<T> {

    /** Marker interface for callback types. */
    public static interface ICallback<T,R> {}

    /** Callback for passing a deferred promise result. */
    public static interface Callback<T,R> extends ICallback<T,R> {
        public R result(T result);
    }

    /** Callback for passing a deferred promise result with an asynchronous chained result. */
    public static interface AsyncCallback<T,R> extends ICallback<T,R> {
        public Deferred<R> result(T result);
    }

    /** Callback for passing a deferred promise error. */
    public static interface ErrorCallback {
        public void error(Exception e);
    }

    /** A deferred continuation. Composed of a then callback and a next deferred. */
    static class Continuation<T,R> {
        ICallback<T,R> thenCallback;
        Deferred<R> next;
        Continuation(ICallback<T,R> cb, Deferred<R> n) {
            thenCallback = cb;
            next = n;
        }
        /** Invoke the continuation. */
        void resolve(T result) {
            try {
                if( thenCallback instanceof Callback ) {
                    R thenResult = ((Callback<T,R>)thenCallback).result( result );
                    next.resolve( thenResult );
                }
                else if( thenCallback instanceof AsyncCallback ) {
                    Deferred<R> thenResult = ((AsyncCallback<T,R>)thenCallback).result( result );
                    next.resolve( thenResult );
                }
            }
            catch(Exception e) {
                next.reject( e );
            }
        }
        /** Invoke the continuation with a rejection. */
        void reject(Exception e) {
            next.reject( e );
        }
    }

    /** The promise result. */
    private T result;
    /** The promise error. */
    private Exception error;
    /** Error callback waiting for promise error. */
    private ErrorCallback errCallback;
    /** The deferred continuation. */
    private Continuation<T,?> continuation;
    /** Flag indicating whether the promise has been resolved. */
    private boolean resolved;
    /** Flag indicating whether the promise has been rejected. */
    private boolean rejected;

    public Deferred() {}

    public Deferred(T result) {
        resolve( result );
    }

    public static <R> Deferred<R> defer(R result) {
        return new Deferred<R>( result );
    }

    /** A set of deferred promise results. */
    private static class DeferredSet<R> {
        List<R> results;
        public DeferredSet() {
            results = new ArrayList<R>();
        }
    }

    /**
     * Wait for all promises in a list to resolve or reject.
     * @param deferreds
     * @return
     */
    public static <R> Deferred<List<R>> all(final List<Deferred<R>> deferreds) {
        final Deferred<List<R>> dresult = new Deferred<List<R>>();
        final DeferredSet<R> dset = new DeferredSet<R>();
        for(Deferred<R> deferred : deferreds) {
            deferred
                    .then(new Callback<R,R>() {
                        @Override
                        public R result(R result) {
                            dset.results.add( result );
                            if( dset.results.size() == deferreds.size() ) {
                                dresult.resolve( dset.results );
                            }
                            return result;
                        }
                    })
                    .error(new ErrorCallback() {
                        @Override
                        public void error(Exception e) {
                            dresult.reject( e );
                        }
                    });
        }
        return dresult;
    }

    /**
     * Resolve the promise by passing a result.
     * @param result
     */
    public void resolve(T result) {
        if( !(resolved || rejected) ) {
            try {
                resolved = true;
                this.result = result;
                if( continuation != null ) {
                    continuation.resolve( result );
                }
            }
            catch(Exception e) {
                reject( e );
            }
        }
    }

    /**
     * Resolve the promise by passing a deferred result.
     */
    public void resolve(Deferred<T> result) {
        if( !(resolved || rejected) ) {
            try {
                result
                        .then(new Callback<T,T>() {
                            @Override
                            public T result(T result) {
                                Deferred.this.resolve( result );
                                return result; // NOTE: Return value not actually used or needed here.
                            }
                        })
                        .error(new ErrorCallback() {
                            @Override
                            public void error(Exception e) {
                                Deferred.this.reject( e );
                            }
                        });
            }
            catch(Exception e) {
                reject( e );
            }
        }
    }

    /**
     * Reject the promise by passing an error.
     * @param e
     */
    public void reject(Exception e) {
        if( !(resolved || rejected) ) {
            rejected = true;
            error = e;
            if( errCallback != null ) {
                errCallback.error( e );
            }
            else if( continuation != null ) {
                continuation.reject( e );
            }
        }
    }

    public void reject(String message) {
        reject( new Exception( message ) );
    }

    /**
     * Add a promise result callback.
     * @param cb
     * @return
     */
    public <R> Deferred<R> then(ICallback<T,R> cb) {
        Deferred<R> next = new Deferred<R>();
        if( resolved ) {
            // Current promise is already resolved, so immediately invoke the callback.
            try {
                if( cb instanceof Callback<?,?> ) {
                    next.resolve( ((Callback<T,R>)cb).result( result ) );
                }
                else if( cb instanceof AsyncCallback<?,?> ) {
                    next.resolve( ((AsyncCallback<T,R>)cb).result( result ) );
                }
            }
            catch(Exception e) {
                next.reject( e );
            }
        }
        else if( rejected ) {
            // If the current promise is already rejected then pass the error onto the
            // next promise.
            next.reject( error );
        }
        else {
            // Promise is neither resolved nor rejected, copy the callback for later usage.
            continuation = new Continuation<T,R>( cb, next );
        }
        return next;
    }

    /**
     * Add a promise reject callback.
     * @param cb
     * @return
     */
    public Deferred<T> error(ErrorCallback cb ) {
        if( rejected ) {
            cb.error( error );
        }
        else if( !resolved ) {
            errCallback = cb;
        }
        return this;
    }

}