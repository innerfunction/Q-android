package q.innerfunction.com.q;

import android.os.Handler;

public class AsyncDeferredUnitTest {
    // A dumb queue, makes an async call and resolve the promise
    private Deferred<Boolean> dumbQueueResolve(Boolean value){
        final Deferred<Boolean> deferredBoolean = new Deferred<Boolean>();
        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        deferredBoolean.resolve(true);
                    }
                }
                , 300);
        return deferredBoolean;
    };

    private Deferred<Boolean> dumbQueueError() {
        final Deferred<Boolean> deferredBoolean = new Deferred<Boolean>();
        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        deferredBoolean.error(new Deferred.ErrorCallback() {
                            @Override
                            public void error(Exception e) {
                                //assert(true);
                            }
                        });
                    }
                }
                , 300);
        return deferredBoolean;
    }

    //    Test
//    public void testCallMultipleFunction(){
//        final Deferred<Boolean> deferredBoolean = new Deferred<Boolean>();
//        List<Deferred<Boolean>> deferredList = new ArrayList<>();
//        deferredList.add(dumbQueueResolve(true));
//        deferredList.add(dumbQueueResolve(false));
//          TODO
//    }
}
