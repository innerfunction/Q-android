package q.innerfunction.com.samplecode;

import android.test.AndroidTestCase;

import org.junit.Test;

import q.innerfunction.com.Deferred;

/**
 * Created by javier on 11/03/2016.
 */
public class SampleOne extends AndroidTestCase{

    Boolean error, success;

    public class Controller {

        // Resolve or reject promises in async calls
        // create a deferred
        //   if you want to show error call: reject()
        //   if you want to resolve the promise call: resolve()
        public Deferred makeAsyncCall(){
            Deferred deferred  = new Deferred();
            // Make async call and when success or error
            if ( success ){
                deferred.resolve(true);
                // This resolve could also be another Deferred
            } else if (error){
                deferred.reject("error");
            }
            return deferred;
        }
        // Returning another defered
        public Deferred getDetailsFromDB(){
            Deferred deferred  = new Deferred();
            final Deferred<Boolean> asyncDefer = makeAsyncCall();

            deferred.then(new Deferred.Callback() {
                @Override
                public Object result(Object result) {
                    // DO SOMETHING
                    asyncDefer.resolve(true);
                    return null;
                }
            });
            return deferred;
        };
        public Deferred getImageFromLibrary(){
            return Deferred.defer(true);
        }
        public Deferred downloadFileFromInternet(){
            return Deferred.defer(true);
        }
    }

    @Test
    public void runSomething(){
        Controller controller = new Controller();

        controller.makeAsyncCall().then(new Deferred.Callback() {
            @Override
            public Object result(Object result) {
                return null;
            }
        });

        // The then method can take Deferred, so chain things

        // Chain operations
        controller.makeAsyncCall()
                .then((Deferred.ICallback) controller.getDetailsFromDB())
                .then((Deferred.ICallback) controller.getImageFromLibrary())
                .then((Deferred.ICallback) controller.downloadFileFromInternet());

        // Control errors
        controller.getDetailsFromDB().error(new Deferred.ErrorCallback() {
            @Override
            public void error(Exception e) {
                //report the error
            }
        });
        // Resolve a number of promises

    }

    @Test
    public void exampleBasic1(){
        // Instantiate a deferred
        Deferred<Boolean> deferred = new Deferred<Boolean>();

        Deferred.ICallback callback = new Deferred.Callback() {
            @Override
            public Object result(Object result) {
                return null;
            }
        };

        deferred.then(callback);



    }



}
