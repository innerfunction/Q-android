package q.innerfunction.com.test;

import android.os.Handler;
import android.os.Looper;
import android.support.test.runner.AndroidJUnit4;
import android.test.AndroidTestCase;

import com.innerfunction.q.Q;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

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

    public Q.Promise<Boolean> promise1() {
        return Q.Promise.defer( true );
    }
    public Q.Promise<Boolean> promise2() {
        return Q.Promise.defer( false );
    }
    public Q.Promise<Boolean> promise3() {
        return Q.Promise.defer( null );
    }

    @Test
    public void testDeferredALL() throws InterruptedException {
        final List<Q.Promise<Boolean>> deferreds = new ArrayList<Q.Promise<Boolean>>();
        deferreds.add(promise1());
        deferreds.add(promise2());
        deferreds.add(promise3());

        semaphore = new Semaphore(1);

        final Q.Promise<Boolean> deferred = new Q.Promise<Boolean>();

        Q.all( deferreds )
            .then(new Q.Promise.AsyncCallback<List<Boolean>, Object>() {
                @Override
                public Q.Promise<Object> result(List<Boolean> result) {
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
