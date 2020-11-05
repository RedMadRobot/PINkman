import java.io.File
import java.util.*

object Android {
    private const val pluginVersion = "4.1.0"

    const val compileSdk = 30
    const val buildTools = "30.0.1"

    const val gradlePlugin = "com.android.tools.build:gradle:$pluginVersion"
    const val applicationPlugin = "com.android.application"
    const val libraryPlugin = "com.android.library"

    object DefaultConfig {
        const val minSdk = 23
        const val targetSdk = 30

        const val applicationId = "com.redmadrobot.pinkman_sample"

        val versionCode = LibraryVersion.code
        val versionName = LibraryVersion.name

        const val instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    object BuildTypes {
        const val release = "release"
    }

    object Proguard {
        const val androidOptimizedRules = "proguard-android-optimize.txt"
        const val projectRules = "proguard-rules.pro"
    }

    private object LibraryVersion {
        val name: String by lazy {
            val properties = Properties().apply {
                load(File("gradle/publish.properties").inputStream())
            }

            properties.getProperty("lib_version")
        }

        val code by lazy {
            val (major, minor, patch) = name.split(".").map { it.toInt() }

            (major * 1000000) + (minor * 10000) + (patch * 100)
        }
    }
}
