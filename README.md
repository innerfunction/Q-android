# Q-android: 

Q-android is a Asynchronous Promise implementation for Android.

This library brings the concept of [JS Promises](https://developer.mozilla.org/en-US/docs/Mozilla/JavaScript_code_modules/Promise.jsm/Promise#Constructor) to Android and it's similar to the [DeferredObject in jQuery](http://api.jquery.com/category/deferred-object/) and the [Android Deferred Object](https://github.com/CodeAndMagic/android-deferred-object).

A Defered is a chainable utility object which can resgister multiple callbacks into callbacks queues, invoque callbacks queues, and relay the success or failure statete of any aysnc or async function. 

Defered allow to run asynchronous functions with callbacks preventing other code from interfering with the progress or status of its internal request. You can define callback chains using the *then* and *error* methods and have access to actual *resolve* and  *reject* (and *Defered.all*) methods which is useful to resolve Promises.

## Usage

Instantiate a Deferred. The Deferred defines the type of return value:
```java
Deferred<Boolean> deferred= new Deferred<>();
```
Then you can define callbacks for your Deferred: 
```java        
deferred
    .then(new Deferred.Callback<Boolean, Void>() {
        @Override
        public Deferred<Boolean> result(Boolean result) {
            assertTrue(result);
            return result;
        }
    })
    .error(new Deferred.ErrorCallback() {
        @Override
        public void error(Exception e) {
            fail();
        }
    });
```
After that you can resolve or reject your Deferred, this is usefull when you want to resolve a deferred in an async call where you need to overwrite methods:
```java
// e.g. after an async operation
if (error != null) {
    defered.reject("error msg");
}else{
    deferred.resolve(true);
}
```
If an "error" occures and the promise is rejected then the error() method in the Deferred gets call. If no error and the resolve method is called then the deferred will be resolved passing the value "true" as the result param in the result callback, so the assert will be true.

## API
The Promise exposes only the Deferred methods needed to attach additional handlers or determine the state: *then*, *error* and the ones to change the state: *resolve*, *reject*:
* then()
* error()
* resolve()
* reject()

### then() continuations
Using *then* we can chain promises, usefull to run secuantially async operations:
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

This opens a lot of options on operations on Defered, like for example build a lists of promises and use the Deferred.app() method to resolve them, see an example:
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
    final List<Deferred<Boolean>> deferreds = new ArrayList<Deferred<Boolean>>();
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

### Static methods:
* Defered.defer(): 
* Defered.all()

### Callbacks objects:
* Deferred.ICallback: This is the main Interface.
* Deferred.Callback: Callback for passing a deferred promise result.
* Deferred.AsyncCallback: Callback for passing a deferred promise result with an asynchronous chained result. The result method returns a Deferred.
* Deferred.ErrorCallback: Callback for passing a deferred promise error. 

## Use cases
Promises became utils when using in conjunction with function which return promises to control async process and write cleaner calls to async methods. 



## Notes
Documentation includes the following:
* high level overview of usage
* detailed description of api usage
* api docs
* and maybe something on use cases
