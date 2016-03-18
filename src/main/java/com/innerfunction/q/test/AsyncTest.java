package com.innerfunction.q.test;

import android.os.Handler;
import android.os.Looper;
import android.support.test.runner.AndroidJUnit4;
import android.test.AndroidTestCase;

import com.innerfunction.q.Q;

import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class AsyncTest extends AndroidTestCase{
    String testString = "teststring";
    Q.Promise<String> deferredString;
    Q.Promise<Object> deferredObject;

    @Test
    public void testResolveAsync() throws InterruptedException {
        deferredString = new Q.Promise();
        deferredString.then(new Q.Promise.Callback<String, Object>() {
            @Override
            public String result(String result) {
                assertEquals(result, testString);
                return result;
            }
        });

        // resolve the promise asyc
        Looper.prepare();
        new Handler().postDelayed( new Runnable() {
                @Override
                public void run() {
                    deferredString.resolve(testString);
                    Looper.myLooper().quit();
                }
            }
            , 300);
        Looper.loop();
    }

    // TODO: Review
    // BackgroundTaskRunner is not working, the run method is never run.
    // Possible causes: JUnit not sparking threads, or need to be called from UI thread
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

