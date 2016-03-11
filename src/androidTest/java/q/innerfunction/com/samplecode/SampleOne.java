package q.innerfunction.com.samplecode;

import android.test.AndroidTestCase;

import org.junit.Test;

import q.innerfunction.com.Deferred;

/**
 * Created by javier on 11/03/2016.
 */
public class SampleOne extends AndroidTestCase{

    public class Controller {
        public Deferred makeAsyncCall(){
            // Here make another sync call
            Deferred deferred  = new Deferred();
            deferred.resolve(true);
            return deferred;
        }
        public Deferred getDetailsFromDB(){
            // create a deferred
            //   if you want to show error call: reject()
            //   if you want to resolve the promise call: resolve()
            return Deferred.defer(true);
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




}
