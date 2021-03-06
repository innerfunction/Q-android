

Q-android is a Asynchronous Promise implementation for Android.

This library brings the concept of [JS Promises](https://developer.mozilla.org/en-US/docs/Mozilla/JavaScript_code_modules/Promise.jsm/Promise#Constructor) to Android and it's similar to the [DeferredObject in jQuery](http://api.jquery.com/category/deferred-object/) and the [Android Deferred Object](https://github.com/CodeAndMagic/android-deferred-object).

A Defered is a chainable utility object which can register multiple callbacks into callbacks queues, invoque callbacks queues, and relay the success or failure statete of any aysnc or async function. 

Defered allow to run pieces of code preventing other code from interfering with the progress or status of its internal request. You can define callback chains using the *then* and *error* methods and have access to actual *resolve* and  *reject* (and *Defered.all*) methods which is useful to resolve Promises.

## Usage

Instantiate a Deferred. The Deferred defines the type of return value:
```java
Q.Promise promise = new Q.Promise();
```
Then you can define callbacks for your Deferred: 
```java        
promise.then( new Q.Promise.Callback<Boolean, Void>(){
         @Override
         public Void result(Boolean result) {
             assertTrue(result);
             return null;
         }
     }
)
.error(new Q.Promise.ErrorCallback() {
    @Override
    public void error(Exception e) {
        fail();
    }
});
```
Then you can resolve or reject your Deferred in any other part of the code.
```java
// e.g. after an async operation
if (error != null) {
    defered.reject("error msg");
}else{
    deferred.resolve(true);
}
```
In the code above if an "error" occurs and the promise is rejected then the error() method in the Deferred gets call. If no error and the resolve method is called then the deferred will be resolved passing the value "true" as the result param in the result callback, so the assert will be true.

## API
The Promise exposes only the Deferred methods needed to attach additional handlers or determine the state: *then*, *error* and the ones to change the state: *resolve*, *reject*:

Main API methods:
* then( callback/promise )
* error( callback/promise )
* resolve( value )
* reject( value )

Static methods:
* Defered.defer(): 
* Defered.all()

Callbacks objects:
* Deferred.ICallback: This is the main Interface.
* Deferred.Callback: Callback for passing a deferred promise result.
* Deferred.AsyncCallback: Callback for passing a deferred promise result with an asynchronous chained result. The result method returns a Deferred.
* Deferred.ErrorCallback: Callback for passing a deferred promise error. 

#### resolve()
Resolve the promise by passing a deferred result or deferred result.

#### reject()
Reject the promise by passing an error.

#### then()
Add a promise result callback. It's useful to run define secuential process with async calls. e.g. Promise1 -> Promise2 -> Promise3. See example: 
```java
    public Deferred<Boolean> promise1() {
        return Deferred.defer( true );
    }
    public Deferred<Boolean> promise2() {
        return Deferred.defer( false );
    }
    public Deferred<Boolean> promise3() { 
        return Deferred.defer( null );
    }
    promise1()
        .then((Deferred.ICallback<Boolean, Object>) promise2())
        .then((Deferred.ICallback<Object, Object>) promise3());    
```
The promise2 won't be resolved until promise1 has finished. promise3 won't be resolved until promise2 has finished.

#### error()
Add a promise reject callback.

#### Deferred.all()
This brings a lot of options on operations on defereds, like for example build a lists of promises and use the Deferred.app() method to resolve them, see an example:
```java
    List<Deferred<Boolean>> deferreds = new ArrayList<Deferred<Boolean>>();
    deferreds.add(promise1());
    deferreds.add(promise2());
    deferreds.add(promise3());
    Deferred.all( deferreds )
            .then(new Deferred.AsyncCallback<List<Boolean>, Object>() {
                @Override
                public Deferred<Object> result(List<Boolean> result) {
                    assertEquals(expectedresult, result);
                    return null;
                }
            });
```

You can find more examples about operations in the [Q test cases](https://github.com/innerfunction/Q-android/tree/master/src/androidTest/java/q/innerfunction/com/test)

## Advantages
Promises became useful when defining function which return other promises. They help to:
* controler process flow with async calls.
* write cleaner calls to async methods. 
* manage error messages over a number of operations.
* multiplatform: Port for iOS.

## Use cases
We will include to some links in code to show more examples.

## Notes
Documentation includes the following:
* high level overview of usage
* detailed description of api usage
* api docs
* and maybe something on use cases
