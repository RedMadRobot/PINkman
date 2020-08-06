![](img/logo.png)

# PINkman
[![API](https://img.shields.io/badge/API-23%2B-red.svg?style=flat)](https://android-arsenal.com/api?level=23)
![CI](https://github.com/RedMadRobot/PINkman/workflows/CI/badge.svg)


Implementing an authentication by a PIN code is an ordinary task for a
mobile applications developer. You can even think of it as some kind of
boilerplate code. But it's a trap. Such tasks have a number of security
gotchas. Therefore there's a high risk of implementing it in an insecure
manner. Don't worry, Pinkman to the rescue!

## What is it?

PINkman is a library to help implementing an authentication by a PIN
code in a secure manner. The library derives hash from the user's PIN
using [Argon2](https://github.com/P-H-C/phc-winner-argon2) hash function  
and stores it in an encrypted file. The file is encrypted with the
AES-256 algorithm in the GCM mode and keys are stored in the
AndroidKeystore.

## How it works?

This library doesn't reinvent it's own cryptograhy and just stands on
the shoulders of giants. Here's the description of the used technologies
and their params.

##### Deriving a hash from a PIN code

For getting the hash, the Argon2 function is used with following params:

- **Mode**: Argon2i
- **Time cost in iterations**: 5
- **Memory cost in KBytes**: 65 536
- **Parallelism**: 2
- **Derived hash length**: 128bit

##### Encrypted files

To store data securely, this library is using the
[Jetpack security](https://developer.android.com/jetpack/androidx/releases/security)
library from the
[Android Jetpack libraries suite](https://developer.android.com/jetpack).
That library, in turn, is using the other awesome library -
[Tink](https://github.com/google/tink), so you can be sure that storing
data of a PIN code is organized quite secure. Or you can verify it
yourself ;)


## Quick start

Add this library to your gradle config

```groovy
implementation 'com.redmadrobot:pinkman:$pinkman_version'
```

Create an instance of the `Pinkman` class (use a DI please) and
integrate it to your authentication logic.

```kotlin
val pinkman = Pinkman(application.applicationContext)

...

class CreatePinViewModel(private val pinkman: Pinkman) : ViewModel() {

    val pinIsCreated = MutableLiveData<Boolean>()

    fun createPin(pin: String) {
        pinkman.createPin(pin)

        pinIsCreated.postValue(true)
    }
}

...

class InputPinViewModel(private val pinkman: Pinkman) : ViewModel() {

    val pinIsValid = MutableLiveData<Boolean>()

    fun validatePin(pin: String) {
        pinIsValid.value = pinkman.isValidPin(pin)
    }
}
```

Also you can do all these things even sipmler with the UI components
(`PinView` and `PinKeyboard`) supplied by this library. You need to add
this dependency to use them

```groovy
implementation 'com.redmadrobot:pinkman-ui:$pinkman_version' 
```

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <com.redmadrobot.pinkman_ui.PinView
        android:id="@+id/pin_view"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginBottom="75dp"
        app:emptyDrawable="@drawable/circle_grey"
        app:filledDrawable="@drawable/circle_red"
        app:itemHeight="22dp"
        app:itemWidth="22dp"
        app:layout_constraintBottom_toTopOf="@+id/keyboard"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:length="4"
        app:spaceBetween="28dp" />

    <com.redmadrobot.pinkman_ui.PinKeyboard
        android:id="@+id/keyboard"
        style="@style/PinkmanDefaultKeyboard"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>

```

And integrate them with the logic written before

```kotlin
class CreatePinFragment : Fragment() {

    private val viewModel: CreatePinViewModel by viewModels()

    ...
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.pinIsCreated.observe(viewLifecycleOwner, Observer { isCreated ->
            findNavController().popBackStack(R.id.mainFragment, false)
        })

        pin_view.onFilledListener = { viewModel.createPin(it) }
        keyboard.keyboardClickListener = { pin_view.add(it) }

    }
}
```

⚠️  
Hash deriving operations can take significant time on some devices. In
order to avoid ANR in your application you shouldn't run methods
`createPin()`, `changePin()`, `isValidPin()` on the main thread.

This library has already provided two extensions to run these methods
asynchronously. You can choose one depending on your specific needs (or
tech stack).

You need to add this dependency if you prefer RxJava:

```groovy
implementation 'com.redmadrobot:pinkman-rx3:$pinkman_version'
```

But if you're on the bleeding edge technologies, you should use a
dependency with
[Kotlin Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html)
support:

```groovy
implementation 'com.redmadrobot:pinkman-coroutines:$pinkman_version'
```

As a result, you'll get RxJava specific or Coroutines specific method's
set:

```kotlin
// RxJava3
fun createPinAsync(...): Completable
fun changePinAsync(...): Completable
fun isValidPinAsync(...): Single<Boolean>

// Coroutines
suspend fun createPinAsync(...)
suspend fun changePinAsync(...)
suspend fun isValidPinAsync(...): Boolean
```

## Feedback

In case you have faced any bugs or have any useful suggestions for
improvement of this library, feel free to create an
[issue](https://github.com/RedMadRobot/PINkman/issues).

## LICENSE

>THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
>OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
>MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
>IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
>CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
>TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
>SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

