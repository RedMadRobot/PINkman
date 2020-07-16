package com.redmadrobot.pinkman

import android.content.Context
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.redmadrobot.pinkman.exception.BlacklistedPinException
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.File

@SmallTest
class PinkmanBlacklistedTest {

    private lateinit var applicationContext: Context

    @Before
    fun setUp() {
        applicationContext = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @After
    fun tearDown() {
        File(applicationContext.filesDir, "pinkman").delete()
    }

    @Test(expected = BlacklistedPinException::class)
    fun createPin_defaultBlacklistedPin() {
        val pinkman = Pinkman(applicationContext, pinBlacklist = Pinkman.DEFAULT_BLACKLIST)

        pinkman.createPin("0000")
    }

    @Test(expected = BlacklistedPinException::class)
    fun createPin_customBlacklistedPin() {
        val pinkman = Pinkman(applicationContext, pinBlacklist = listOf("8557"))

        pinkman.createPin("8557")
    }

    @Test(expected = BlacklistedPinException::class)
    fun changePin_defaultBlacklistedPin() {
        val pinkman = Pinkman(applicationContext, pinBlacklist = Pinkman.DEFAULT_BLACKLIST)

        with(pinkman) {
            createPin("1337")
            changePin("1337", "0000")
        }
    }

    @Test(expected = BlacklistedPinException::class)
    fun changePin_customBlacklistedPin() {
        val pinkman = Pinkman(applicationContext, pinBlacklist = listOf("8557"))

        with(pinkman) {
            createPin("0000")
            changePin("0000", "8557")
        }
    }
}
