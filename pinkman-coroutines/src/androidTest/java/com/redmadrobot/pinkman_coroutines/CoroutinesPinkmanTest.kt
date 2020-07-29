package com.redmadrobot.pinkman_coroutines

import android.content.Context
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.redmadrobot.pinkman.Pinkman
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.File


@SmallTest
class CoroutinesPinkmanTest {
    private lateinit var applicationContext: Context
    private lateinit var pinkman: Pinkman

    private val testDispatcher = TestCoroutineDispatcher()

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
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun createPin() {
        runBlockingTest {
            pinkman.createPinAsync("0000", coroutineContext = testDispatcher)

            Assert.assertTrue(
                "PIN storage wasn't created",
                File(applicationContext.filesDir, "pinkman").exists()
            )
        }
    }

    @Test
    fun changePin() {
        runBlockingTest {
            pinkman.createPinAsync("0000", coroutineContext = testDispatcher)

            pinkman.changePinAsync("0000", "1111", coroutineContext = testDispatcher)

            Assert.assertTrue(pinkman.isValidPin("1111"))
        }
    }

    @Test
    fun isValidPin() {
        runBlockingTest {
            pinkman.createPinAsync("0000", coroutineContext = testDispatcher)

            Assert.assertTrue(pinkman.isValidPinAsync("0000", coroutineContext = testDispatcher))
        }
    }
}
