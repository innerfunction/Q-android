package q.innerfunction.com.test;

import android.os.Handler;
import android.os.Looper;
import android.test.AndroidTestCase;

import com.innerfunction.q.Q;

import org.junit.Test;
import java.util.concurrent.Semaphore;

public class InstrumentationTest extends AndroidTestCase{
    String testString = "teststring";
    Semaphore semaphore;
    Q.Promise<String> deferredString;


    @Test
    public void testOne(){
        assertTrue(true);
    }

    public void runAsyncHandler() throws InterruptedException {
        Looper.prepare();
        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        System.out.print("run!!!");
                        Looper.myLooper().quit();
                        assertTrue(true);
                    }
                }
                , 300);
        Looper.loop();
    }

    @Test
    public void testAsync() throws InterruptedException {
        //final Semaphore semaphore = new Semaphore(1);
        Looper.prepare();
        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        System.out.print("run!!!");
                        Looper.myLooper().quit();
                        assertTrue(true);
                        //              semaphore.release();
                    }
                }
                , 300);
        Looper.loop();
        //semaphore.acquire();
        System.out.print("pass!!");
    }

    // A dumb queue, makes an async call and resolve the promise
//    private Deferred<Boolean> dumbQueueResolve(Boolean value) throws InterruptedException {
//        final Semaphore semaphore = new Semaphore(1);
//        final Deferred<Boolean> deferredBoolean = new Deferred<Boolean>();
//        new Handler().postDelayed(
//                new Runnable() {
//                    @Override
//                    public void run() {
//                        System.out.print("run!!!");
//                        semaphore.release();
//                        deferredBoolean.resolve(true);
//                    }
//                }
//                , 300);
//        semaphore.acquire();
//        System.out.print("pass!!");
//        return deferredBoolean;
//    };

//    @Test
//    public void testAsyncResolve() throws InterruptedException {
//        dumbQueueResolve(true);
//    }

    @Test
    public void testString() throws InterruptedException {
        semaphore = new Semaphore(1);
        deferredString = new Q.Promise<String>();

        deferredString.then(new Q.Promise.Callback<String, Object>() {
            @Override
            public String result(String result) {
                assertEquals(result, testString);
                semaphore.release();
                return result;
            }
        });

        Looper.prepare();
        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        System.out.print("run!!!");
                        semaphore.release();
                        deferredString.resolve(testString);
                        Looper.myLooper().quit();
                    }
                }
                , 300);
        Looper.loop();
        semaphore.acquire();
    }

    @Test
    public void testStringAsyncCall1() throws InterruptedException {
        final Semaphore semaphore = new Semaphore(1);
        final Q.Promise<String> deferredString = new Q.Promise<String>();
        deferredString.then(new Q.Promise.Callback<String, Object>() {
            @Override
            public String result(String result) {
                assertEquals(result, testString);
                semaphore.release();
                return result;
            }
        });

//        BackgroundTaskRunner.run(new Runnable() {
//            @Override
//            public void run() {
//                Looper.prepare();
//                Handler h = new Handler();
//                h.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        deferredString.resolve(testString);
//                        Looper.myLooper().quit();
//                    }
//                }, 200);
//                Looper.loop();
//            }
//        });
        semaphore.acquire();
    }

}

