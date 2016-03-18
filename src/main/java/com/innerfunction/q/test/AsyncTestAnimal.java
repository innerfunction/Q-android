package com.innerfunction.q.test;

import android.os.Handler;
import android.os.Looper;
import android.support.test.runner.AndroidJUnit4;
import android.test.AndroidTestCase;

import com.innerfunction.q.Q;


import org.junit.Test;
import org.junit.runner.RunWith;

import com.innerfunction.q.testutils.Animal;

@RunWith(AndroidJUnit4.class)
public class AsyncTestAnimal extends AndroidTestCase{
    String testString = "teststring";
    Q.Promise<String> deferredString;
    Q.Promise<Object> deferredObject;

    @Test
    public void testAnimal() {
        deferredObject = new Q.Promise<>();
        final Animal monkey = new Animal();
        monkey.setName("Name");

        deferredObject.then(new Q.Promise.Callback<Object, Object>() {
            @Override
            public Object result(Object result) {
                assertSame(monkey, (Animal) result);
                assertEquals(monkey.getName(), ((Animal) result).getName());
                return result;
            }
        });

        Looper.prepare();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                deferredObject.resolve(monkey);
                Looper.myLooper().quit();
            }
        }, 200);
        Looper.loop();
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

