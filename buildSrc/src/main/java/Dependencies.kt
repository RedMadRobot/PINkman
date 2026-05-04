object Dependencies {
    object Common {
        private object Versions {
            const val appCompat = "1.7.0"
        }

        const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    }

    object Lib {
        private object Versions {
            const val securityCrypto = "1.0.0"
            const val argon2 = "1.6.0"
            const val coroutines = "1.4.2"
            const val rxJava3 = "3.1.4"
        }

        const val securityCrypto = "androidx.security:security-crypto:${Versions.securityCrypto}"
        const val argon2 = "com.lambdapioneer.argon2kt:argon2kt:${Versions.argon2}"
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
        const val rxJava3 = "io.reactivex.rxjava3:rxjava:${Versions.rxJava3}"
    }

    object App {
        private object Versions {
            const val hilt = "2.57"

            const val navigationFragmentKtx = "2.9.7"
            const val navigationUiKtx = "2.9.7"

            const val lifecycleViewmodelKtx = "2.2.0"
            const val lifecycleLivedataKtx = "2.2.0"

            const val coreKtx = "1.3.0"
            const val constraintlayout = "1.1.3"
        }

        const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hilt}"

        const val navigationFragmentKtx = "androidx.navigation:navigation-fragment-ktx:${Versions.navigationFragmentKtx}"
        const val navigationUiKtx = "androidx.navigation:navigation-ui-ktx:${Versions.navigationUiKtx}"

        const val lifecycleViewmodelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleViewmodelKtx}"
        const val lifecycleLivedataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycleLivedataKtx}"

        const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
        const val constraintlayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}"

        const val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
        const val hiltAppPlugin = "dagger.hilt.android.plugin"
        const val hiltGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
    }
}
