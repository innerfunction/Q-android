# Q-android: Asynchronous Promise implementation for Android.

This library brings the concept of deferred promises from the JS world to Android.  

Promises allow to run asynchronous functions to prevent other code from interfering with the progress or status of its internal request.

Promises are useful when you want to make async calls in your code which may be rejected, and also as a way to write more readable code.

More about [JS Promises:](https://developer.mozilla.org/en-US/docs/Mozilla/JavaScript_code_modules/Promise.jsm/Promise#Constructor)

## Usage
```java
        Deferred<Boolean> deferred= new Deferred<>();
        deferred.resolve(true);

        deferred
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

````

## API
The Promise exposes only the Deferred methods needed to attach additional handlers or determine the state:
* then:
* error
and the ones to change the state:
* resolve
* reject

## Use cases



## Notes
Documentation includes the following:
* high level overview of usage
* detailed description of api usage
* api docs
* and maybe something on use cases
