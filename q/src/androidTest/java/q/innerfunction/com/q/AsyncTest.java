package q.innerfunction.com.q;

import android.os.Handler;
import android.os.Looper;
import android.support.test.runner.AndroidJUnit4;
import android.test.AndroidTestCase;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.Semaphore;

import q.innerfunction.com.BackgroundTaskRunner;
import q.innerfunction.com.Deferred;

@RunWith(AndroidJUnit4.class)
public class AsyncTest extends AndroidTestCase{
    String testString = "teststring";
    Semaphore semaphore;
    Deferred<String> deferredString;

    @Test
    public void testAsyncResolve() throws InterruptedException {
        semaphore = new Semaphore(1);
        deferredString = new Deferred<String>();

        deferredString.then(new Deferred.Callback<String, Object>() {
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

    // TODO: Review
    // BackgroundTaskRunner is not working, the run method is never run.
    // Possible causes: JUnit not sparking threads, or need to be called from UI thead
//    @Test
//    public void testAsyncCall() throws InterruptedException {
//        semaphore = new Semaphore(1);
//        BackgroundTaskRunner.run(new Runnable() {
//            @Override
//            public void run() {
//                assertTrue(true);
//                Looper.prepare();
//                new Handler().postDelayed(
//                        new Runnable() {
//                            @Override
//                            public void run() {
//                                assertTrue(true);
//                                Looper.myLooper().quit();
//                                semaphore.release();
//                            }
//                        }
//                        , 300);
//                Looper.loop();
//            }
//        });
//        semaphore.acquire();
//    }

}

