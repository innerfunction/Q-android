package q.innerfunction.com.q;

import android.os.Handler;
import android.os.Looper;
import android.test.AndroidTestCase;

import org.junit.Test;

import java.util.concurrent.Semaphore;

import static junit.framework.Assert.*;

public class AsyncDeferredUnitTest  {

    private String testString = "test";

    // A dumb queue, makes an async call and resolve the promise
    private Deferred<Boolean> dumbQueueResolve(Boolean value) throws InterruptedException {
        final Semaphore semaphore = new Semaphore(1);
        final Deferred<Boolean> deferredBoolean = new Deferred<Boolean>();
        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        System.out.print("run!!!");
                        semaphore.release();
                        deferredBoolean.resolve(true);
                    }
                }
                , 300);
        semaphore.acquire();
        System.out.print("pass!!");
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

    @Test
    public void testRunInThread() throws InterruptedException{
        dumbQueueResolve(true);
    }

    @Test
    public void testStringAsyncCall() throws InterruptedException {
        final Semaphore semaphore = new Semaphore(1);
        final Deferred<String> deferredString = new Deferred<String>();
        deferredString.then(new Deferred.Callback<String, Object>() {
            @Override
            public String result(String result) {
                assertEquals(result, testString);
                semaphore.release();
                return result;
            }
        });

        BackgroundTaskRunner.run(new Runnable(){
            @Override
            public void run() {
                Looper.prepare();
                Handler h = new Handler();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        deferredString.resolve(testString);
                        Looper.myLooper().quit();
                    }
                },200);
                Looper.loop();
            }
        });
        semaphore.acquire();
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
