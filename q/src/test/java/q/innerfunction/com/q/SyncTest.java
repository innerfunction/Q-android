package q.innerfunction.com.q;

import org.junit.Before;
import org.junit.Test;

import q.innerfunction.com.Deferred;
import q.innerfunction.com.test.utils.Animal;
import static org.junit.Assert.*;

public class SyncTest {
    Deferred<String> deferredString;
    Deferred<Object> deferredObject;
    Deferred<Deferred<Object>> deferredPromise;
    String stringPromise = "string";
    Animal monkey;

    @Before
    public void setUp() {
        deferredString = new Deferred<String>();
        stringPromise= "Promise String";
        deferredObject = new Deferred<Object>();
        monkey = new Animal();
        deferredPromise = new Deferred<Deferred<Object>>();
    }

    @Test
    public void testReturnString() {
        deferredString.resolve(stringPromise);
        deferredString.then(new Deferred.Callback<String, Object>() {
            @Override
            public String result(String result) {
                assertEquals(stringPromise, result);
                return result;
            }
        });
    }

    @Test
    public void testReturnObject() {
        monkey.setName("Name");
        deferredObject.resolve(monkey);
        deferredObject.then(new Deferred.Callback<Object, Object>() {
            @Override
            public Object result(Object result) {
                Animal resultAnimal= (Animal) result;
                assertSame(result, monkey);
                assertEquals(resultAnimal.getName(), monkey.getName());
                return result;
            }
        });

    }

    public void testReturnNull() {
        deferredObject.resolve(null);
        deferredObject.then(new Deferred.Callback<Object, Object>() {
            @Override
            public Object result(Object result) {
                assertSame(result, null);
                return result;
            }

        });
    }

    public void testReturnPromise() {
        deferredPromise.resolve(deferredObject);
        deferredPromise.then(new Deferred.Callback<Deferred<Object>, Object>() {
            @Override
            public Deferred<Object> result(Deferred<Object> result) {
                assertSame(result, deferredObject);
                return result;
            }
        });
    }

}
