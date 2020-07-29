package com.redmadrobot.pinkman_coroutines

import com.redmadrobot.pinkman.Pinkman
import com.redmadrobot.pinkman.exception.BlacklistedPinException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

@Throws(BlacklistedPinException::class)
suspend fun Pinkman.createPinAsync(
    newPin: String,
    force: Boolean = false,
    coroutineContext: CoroutineContext = Dispatchers.Default
) {
    withContext(coroutineContext) {
        createPin(newPin, force)
    }
}

@Throws(BlacklistedPinException::class, IllegalArgumentException::class)
suspend fun Pinkman.changePinAsync(
    oldPin: String,
    newPin: String,
    coroutineContext: CoroutineContext = Dispatchers.Default
) {
    withContext(coroutineContext) {
        changePin(oldPin, newPin)
    }
}

@Throws(IllegalArgumentException::class)
suspend fun Pinkman.isValidPinAsync(
    inputPin: String,
    coroutineContext: CoroutineContext = Dispatchers.Default
): Boolean {
    return withContext(coroutineContext) {
        isValidPin(inputPin)
    }
}
