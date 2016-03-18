package com.innerfunction.q.samplecode;

import android.test.AndroidTestCase;

import com.innerfunction.q.Q;

import org.junit.Test;

/**
 * Created by jloriente on 11/03/2016.
 */
public class SampleOne extends AndroidTestCase{

    Boolean error, success;

    public class Controller {

        // Resolve or reject promises in async calls
        // create a deferred
        //   if you want to show error call: reject()
        //   if you want to resolve the promise call: resolve()
        public Q.Promise makeAsyncCall(){
            Q.Promise deferred  = new Q.Promise<>();
            // Make async call and when success or error
            if ( success ){
                deferred.resolve(true);
                // This resolve could also be another Deferred
            } else if (error){
                deferred.reject("error");
            }
            return deferred;
        }
        // Returning another defered
        public Q.Promise getDetailsFromDB(){
            Q.Promise deferred  = new Q.Promise<>();
            final Q.Promise<Boolean> asyncDefer = makeAsyncCall();

            deferred.then(new Q.Promise.Callback() {
                @Override
                public Object result(Object result) {
                    // DO SOMETHING
                    asyncDefer.resolve(true);
                    return null;
                }
            });
            return deferred;
        };
        public Q.Promise getImageFromLibrary(){
            return Q.Promise.defer(true);
        }
        public Q.Promise downloadFileFromInternet(){
            return Q.Promise.defer(true);
        }
    }

    @Test
    public void runSomething(){
        Controller controller = new Controller();

        controller.makeAsyncCall().then(new Q.Promise.Callback() {
            @Override
            public Object result(Object result) {
                return null;
            }
        });

        // The then method can take Q.Promise, so chain things

        // Chain operations
        controller.makeAsyncCall()
                .then((Q.Promise.ICallback) controller.getDetailsFromDB())
                .then((Q.Promise.ICallback) controller.getImageFromLibrary())
                .then((Q.Promise.ICallback) controller.downloadFileFromInternet());

        // Control errors
        controller.getDetailsFromDB().error(new Q.Promise.ErrorCallback() {
            @Override
            public void error(Exception e) {
                //report the error
            }
        });
        // Resolve a number of promises

    }

    @Test
    public void exampleBasic1(){
        // Instantiate a deferred
        Q.Promise<Boolean> deferred = new Q.Promise<Boolean>();

        Q.Promise.ICallback callback = new Q.Promise.Callback() {
            @Override
            public Object result(Object result) {
                return null;
            }
        };

        deferred.then(callback);
    }

    @Test
    public void staticCallsExamples(){

        // old code
        Q.Promise promise = new Q.Promise<>();

        // TODO: How to call next
        //Q.Promise newPromise = Q.resolve( promise );

        Q.Promise rejectPromise = Q.reject("Error");

    }


}
