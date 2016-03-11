package q.innerfunction.com.test;

import android.os.Handler;
import android.os.Looper;
import android.support.test.runner.AndroidJUnit4;
import android.test.AndroidTestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import q.innerfunction.com.Deferred;

@RunWith(AndroidJUnit4.class)
public class DeferredAllTestAsync extends AndroidTestCase {
    ArrayList<Boolean> expectedresult;
    Semaphore semaphore;

    @Before
    public void setUp() throws Exception {
        expectedresult= new ArrayList<Boolean>();
        expectedresult.add(true);
        expectedresult.add(false);
        expectedresult.add(null);
    }

    public Deferred<Boolean> promise1() {
        return Deferred.defer( true );
    }
    public Deferred<Boolean> promise2() {
        return Deferred.defer( false );
    }
    public Deferred<Boolean> promise3() {
        return Deferred.defer( null );
    }

    @Test
    public void testDeferredALL() throws InterruptedException {
        final List<Deferred<Boolean>> deferreds = new ArrayList<Deferred<Boolean>>();
        deferreds.add(promise1());
        deferreds.add(promise2());
        deferreds.add(promise3());

        semaphore = new Semaphore(1);
        
        final Deferred<Boolean> deferred = new Deferred<Boolean>();

        Deferred.all( deferreds )
            .then(new Deferred.AsyncCallback<List<Boolean>, Object>() {
                @Override
                public Deferred<Object> result(List<Boolean> result) {
                    assertEquals(expectedresult, result);
                    semaphore.release();
                    // return result;
                    return null;
                }
            });

        Looper.prepare();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                deferred.resolve(true);
                Looper.myLooper().quit();
            }
        }, 200);
        Looper.loop();

        semaphore.acquire();
    }
}
