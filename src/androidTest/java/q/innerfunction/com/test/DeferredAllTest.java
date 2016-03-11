package q.innerfunction.com.test;

import android.test.AndroidTestCase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import q.innerfunction.com.Deferred;

public class DeferredAllTest {
    ArrayList<Boolean> expectedresult;

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
    public Deferred<Boolean> promise3() { return Deferred.defer( null );}

    @Test
    public void testDeferredALL() {
        final List<Deferred<Boolean>> deferreds = new ArrayList<Deferred<Boolean>>();
        deferreds.add(promise1());
        deferreds.add(promise2());
        deferreds.add(promise3());

        final Deferred<Boolean> deferred = new Deferred<Boolean>();
        deferred.then(new Deferred.Callback<Boolean, Object>() {
            @Override
            public Object result(Boolean result) {
                Assert.assertTrue(result);
                return true;
            }
        });


        Deferred.all( deferreds )
            .then(new Deferred.Callback<List<Boolean>, Object>() {
                @Override
                public Object result(List<Boolean> result) {
                    Assert.assertEquals(expectedresult, result);
                    deferred.resolve(true);
                    return null;
                }
            });
    };
               
}
