# Q-android: 

Q-android is a Asynchronous Promise implementation for Android.

This library brings the concept of Promises from the JS world to Android.  More about [JS Promises:](https://developer.mozilla.org/en-US/docs/Mozilla/JavaScript_code_modules/Promise.jsm/Promise#Constructor)

A Defered is a chainable utility object whic can resgister multiple callbacks into queues, invoque callbacks queues, and relay the success or failure statete of any aysnc or async function. Similar to the [Android Deferred Object](https://github.com/CodeAndMagic/android-deferred-object)

Defered allow to run asynchronous functions with callbacks preventing other code from interfering with the progress or status of its internal request. You have access to actual *resolve* and *reject* methods which is usedul when you wnat to write your own Deferred.

## Usage

```java

Deferred<Boolean> deferred= new Deferred<>();
deferred.resolve(true);
        
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
````

## API
The Promise exposes only the Deferred methods needed to attach additional handlers or determine the state: *then*, *error* and the ones to change the state: *resolve*, *reject*

## Use cases
Promises became utils when using in conjunction with function which return Promises. This way it's easier to control process and is cleaner to calls to async methods. 


## Notes
Documentation includes the following:
* high level overview of usage
* detailed description of api usage
* api docs
* and maybe something on use cases
