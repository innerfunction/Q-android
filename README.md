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
            //fail();
        }
    });
```
After that you can resolve or reject your Deferred:
```java
deferred.resolve(true)
```
When the resolve method is called the deferred will be resolved passing the value "true" as the result param in the result callback, so the assert will be true.

## API
The Promise exposes only the Deferred methods needed to attach additional handlers or determine the state: *then*, *error* and the ones to change the state: *resolve*, *reject*:
* then()
* error()
* resolve()
* reject()

Static methods:
* Defered.defer(): 
* Defered.all()

Callbacks Objects:
* Defered.ICallback: interface
* Defered.Callback
* Defered.AsyncCallback
* Defered.ErrorCallback

## Use cases
Promises became utils when using in conjunction with function which return promises to control async process and write cleaner calls to async methods. 

Async callbacks

Examples in git:

## Notes
Documentation includes the following:
* high level overview of usage
* detailed description of api usage
* api docs
* and maybe something on use cases
