package com.redmadrobot.pinkman_rx3

import com.redmadrobot.pinkman.Pinkman
import com.redmadrobot.pinkman.exception.BlacklistedPinException
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

@Throws(BlacklistedPinException::class)
fun Pinkman.createPinAsync(
    newPin: String,
    force: Boolean = false,
    scheduler: Scheduler = Schedulers.computation()
): Completable {
    return Completable.fromCallable { createPin(newPin, force) }
        .subscribeOn(scheduler)

}

@Throws(BlacklistedPinException::class, IllegalArgumentException::class)
fun Pinkman.changePinAsync(
    oldPin: String,
    newPin: String,
    scheduler: Scheduler = Schedulers.computation()
): Completable {
    return Completable.fromCallable { changePin(oldPin, newPin) }
        .subscribeOn(scheduler)
}

@Throws(IllegalArgumentException::class)
fun Pinkman.isValidPinAsync(
    inputPin: String,
    scheduler: Scheduler = Schedulers.computation()
): Single<Boolean> {
    return Single.fromCallable { isValidPin(inputPin) }
        .subscribeOn(scheduler)
}
