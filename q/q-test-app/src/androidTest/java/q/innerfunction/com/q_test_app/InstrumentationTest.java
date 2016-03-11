package q.innerfunction.com.q_test_app;

import android.os.Handler;
import android.os.Looper;
import android.test.AndroidTestCase;


import org.junit.Test;

import java.util.concurrent.Semaphore;

public class InstrumentationTest extends AndroidTestCase{

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

    public void runAsyncSemaphore {

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


//    // A dumb queue, makes an async call and resolve the promise
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
//
//    @Test
//    public void testRunInThread() throws InterruptedException{
//        dumbQueueResolve(true);
//    }
}

