package q.innerfunction.com.test;

import org.junit.Test;

import java.lang.Boolean;

import q.innerfunction.com.Deferred;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.*;

public class DeferredUnitTest {

    private Deferred<Void> getVoidDeferred(){
        Deferred<Void> deferred = new Deferred<Void>();
        return deferred;
    }

    @Test
    public void instantiateDeferred(){
        Deferred<Boolean> deferred = new Deferred<Boolean>();
        assertNotNull(deferred);

        Deferred<Boolean> de = new Deferred<>(true);
    }

    // TEST
    // sync: resolve, error callback

    @Test
    public void testDefered_resolved(){
        Deferred<Boolean> deferred= new Deferred<>();
        deferred.resolve(true);

        deferred
            .then(new Deferred.Callback<Boolean, Object>() {
                @Override
                public Boolean result(Boolean result) {
                    assertTrue(result);
                    return result;
                }
            })
            .error(new Deferred.ErrorCallback() {
                @Override
                public void error(Exception e) {
                    //fail();
                }
            });
    };

    @Test
    public void testDeferredError(){
        Deferred<Boolean> deferred = new Deferred<>();
        deferred.reject("error");

        deferred
            .then(new Deferred.Callback<Boolean, Object>() {
                      @Override
                      public Boolean result(Boolean result) {
                          fail();
                          return result;
                      }
                  }
            )
            .error(new Deferred.ErrorCallback() {
                @Override
                public void error(Exception e) {
                    // ensure error method is call
                    assertTrue(true);
                }
            });
    };

}
