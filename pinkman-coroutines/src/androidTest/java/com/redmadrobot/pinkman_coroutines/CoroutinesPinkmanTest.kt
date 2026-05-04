package com.redmadrobot.pinkman_coroutines

import android.content.Context
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.redmadrobot.pinkman.Pinkman
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.File

@OptIn(ExperimentalCoroutinesApi::class)
@SmallTest
class CoroutinesPinkmanTest {
    private lateinit var applicationContext: Context
    private lateinit var pinkman: Pinkman

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        applicationContext = InstrumentationRegistry.getInstrumentation().targetContext
        pinkman = Pinkman(applicationContext)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        File(applicationContext.filesDir, "pinkman").delete()
        Dispatchers.resetMain()
    }

    @Test
    fun createPin() {
        runTest {
            pinkman.createPinAsync("0000", coroutineContext = testDispatcher)

            Assert.assertTrue(
                "PIN storage wasn't created",
                File(applicationContext.filesDir, "pinkman").exists()
            )
        }
    }

    @Test
    fun changePin() {
        runTest {
            pinkman.createPinAsync("0000", coroutineContext = testDispatcher)

            pinkman.changePinAsync("0000", "1111", coroutineContext = testDispatcher)

            Assert.assertTrue(pinkman.isValidPin("1111"))
        }
    }

    @Test
    fun isValidPin() {
        runTest {
            pinkman.createPinAsync("0000", coroutineContext = testDispatcher)

            Assert.assertTrue(pinkman.isValidPinAsync("0000", coroutineContext = testDispatcher))
        }
    }
}
