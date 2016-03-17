package q.innerfunction.com.test;

import com.innerfunction.q.Q;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DeferredAllTest {
    ArrayList<Boolean> expectedresult;

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
    public void testPromiseALL() {
        final List<Q.Promise<Boolean>> deferreds = new ArrayList<Q.Promise<Boolean>>();
        deferreds.add(promise1());
        deferreds.add(promise2());
        deferreds.add(promise3());

        promise1()
            .then((Q.Promise.ICallback<Boolean, Object>) promise2())
            .then((Q.Promise.ICallback<Object, Object>) promise3());

        final Q.Promise<Boolean> deferred = new Q.Promise<Boolean>();
        deferred.then(new Q.Promise.Callback<Boolean, Object>() {
            @Override
            public Object result(Boolean result) {
                Assert.assertTrue(result);
                return true;
            }
        });


        Q.all( deferreds )
            .then(new Q.Promise.Callback<List<Boolean>, Object>() {
                @Override
                public Object result(List<Boolean> result) {
                    Assert.assertEquals(expectedresult, result);
                    deferred.resolve(true);
                    return null;
                }
            });
    };
               
}
