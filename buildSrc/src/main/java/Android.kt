object Android {
    const val compileSdk = 36
    const val buildTools = "36.0.0"

    object DefaultConfig {
        const val minSdk = 23
        const val targetSdk = 36

        const val applicationId = "com.redmadrobot.pinkman_sample"

        const val instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    object BuildTypes {
        const val release = "release"
    }

    object Proguard {
        const val androidOptimizedRules = "proguard-android-optimize.txt"
        const val projectRules = "proguard-rules.pro"
    }
}
