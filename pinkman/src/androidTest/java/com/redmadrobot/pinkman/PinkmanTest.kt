package com.redmadrobot.pinkman

import android.content.Context
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.File

@SmallTest
class PinkmanTest {

    private lateinit var applicationContext: Context
    private lateinit var pinkman: Pinkman

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
        pinkman.createPin("0000")

        assertTrue(
            "PIN storage wasn't created",
            File(applicationContext.filesDir, "pinkman").exists()
        )
    }

    @Test
    fun removePin() {
        pinkman.createPin("0000")

        val isRemoved = pinkman.removePin()

        assertTrue("PIN storage wasn't removed", isRemoved)
        assertTrue(
            "PIN storage still exists after a remove operation",
            !File(applicationContext.filesDir, "pinkman").exists()
        )
    }

    @Test
    fun changePin() {
        pinkman.createPin("0000")

        pinkman.changePin("0000", "1111")

        assertTrue(pinkman.isValidPin("1111"))
    }

    @Test
    fun isValidPin() {
        pinkman.createPin("0000")

        assertTrue(pinkman.isValidPin("0000"))
    }

    @Test
    fun isPinSet() {
        pinkman.createPin("0000")

        assertTrue(pinkman.isPinSet())
    }
}
