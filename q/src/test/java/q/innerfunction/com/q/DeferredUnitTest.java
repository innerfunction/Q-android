package q.innerfunction.com.q;

import android.os.Handler;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
        Deferred<Boolean> initialDeferred = new Deferred<>();
        initialDeferred.resolve(true);

        initialDeferred
            .then(new Deferred.AsyncCallback<Boolean, Void>() {
                @Override
                public Deferred<Void> result(Boolean result) {
                    assertTrue(result);
                    return null;
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
        Deferred<Boolean> initialDeferred = new Deferred<>();
        initialDeferred.reject("error");

        initialDeferred
            .then(new Deferred.AsyncCallback<Boolean, Void>() {
                      @Override
                      public Deferred<Void> result(Boolean result) {
                          fail();
                          return null;
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
