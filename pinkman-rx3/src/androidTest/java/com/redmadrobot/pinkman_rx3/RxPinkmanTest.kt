package com.redmadrobot.pinkman_rx3

import android.content.Context
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.redmadrobot.pinkman.Pinkman
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.File


@SmallTest
class RxPinkmanTest {
    private lateinit var applicationContext: Context
    private lateinit var pinkman: Pinkman

    private val testScheduler = Schedulers.trampoline()

    @Before
    fun setUp() {
        applicationContext = InstrumentationRegistry.getInstrumentation().targetContext
        pinkman = Pinkman(applicationContext)
    }

    @After
    fun tearDown() {
        File(applicationContext.filesDir, "pinkman").delete()
    }

    @Test
    fun createPin() {
        pinkman.createPinAsync("0000", scheduler = testScheduler)
            .andThen(Single.fromCallable { File(applicationContext.filesDir, "pinkman").exists() })
            .test()
            .assertValue(true)
}

@Test
fun changePin() {
    pinkman.createPinAsync("0000", scheduler = testScheduler)
        .andThen(pinkman.changePinAsync("0000", "1111", scheduler = testScheduler))
        .andThen(pinkman.isValidPinAsync("1111", scheduler = testScheduler))
        .test()
        .assertValue(true)
}

@Test
fun isValidPin() {
    pinkman.createPinAsync("0000", scheduler = testScheduler)
        .andThen(pinkman.isValidPinAsync("0000", scheduler = testScheduler))
        .test()
        .assertValue(true)
}
}
