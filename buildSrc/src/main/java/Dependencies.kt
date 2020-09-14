object Dependencies {
    object Common {
        private object Versions {
            const val appCompat = "1.2.0"
        }

        const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    }

    object Lib {
        private object Versions {
            const val securityCrypto = "1.0.0-rc02"
            const val argon2 = "1.1.0"
            const val coroutines = "1.3.9"
            const val rxJava3 = "3.0.6"
        }

        const val securityCrypto = "androidx.security:security-crypto:${Versions.securityCrypto}"
        const val argon2 = "com.lambdapioneer.argon2kt:argon2kt:${Versions.argon2}"
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
        const val rxJava3 = "io.reactivex.rxjava3:rxjava:${Versions.rxJava3}"
    }

    object App {
        private object Versions {
            const val daggerVersion = "2.29-alpha"
            const val hiltLifecycleViewmodel = "1.0.0-alpha01"

            const val navigationFragmentKtx = "2.3.0-rc01"
            const val navigationUiKtx = "2.3.0-rc01"

            const val lifecycleViewmodelKtx = "2.2.0"
            const val lifecycleLivedataKtx = "2.2.0"

            const val coreKtx = "1.3.0"
            const val constraintlayout = "1.1.3"

            const val hiltCompiler = "1.0.0-alpha01"
            const val hiltAndroidCompiler = "2.28.3-alpha"
        }

        const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.daggerVersion}"
        const val hiltLifecycleViewmodel = "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hiltLifecycleViewmodel}"

        const val navigationFragmentKtx = "androidx.navigation:navigation-fragment-ktx:${Versions.navigationFragmentKtx}"
        const val navigationUiKtx = "androidx.navigation:navigation-ui-ktx:${Versions.navigationUiKtx}"

        const val lifecycleViewmodelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleViewmodelKtx}"
        const val lifecycleLivedataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycleLivedataKtx}"

        const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
        const val constraintlayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}"

        const val hiltCompiler = "androidx.hilt:hilt-compiler:${Versions.hiltCompiler}"
        const val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hiltAndroidCompiler}"
        const val hiltAppPlugin = "dagger.hilt.android.plugin"
        const val hiltGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.daggerVersion}"
    }
}
