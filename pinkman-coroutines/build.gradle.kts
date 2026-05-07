plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    convention.publishing
}

description = "This artifact adds coroutines support to PINkman"

android {
    namespace = "com.redmadrobot.pinkman_coroutines"

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(17)
        }
    }

    compileSdkVersion(Android.compileSdk)

    defaultConfig {
        minSdk = Android.DefaultConfig.minSdk

        testInstrumentationRunner = Android.DefaultConfig.instrumentationRunner

        consumerProguardFile("consumer-rules.pro")
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }

    buildTypes {
        getByName(Android.BuildTypes.release) {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile(Android.Proguard.androidOptimizedRules),
                Android.Proguard.projectRules
            )
        }
    }

    packaging {
        resources {
            excludes += setOf(
                "META-INF/AL2.0",
                "META-INF/LGPL2.1"
            )
        }
    }
}

dependencies {
    api(project(":pinkman"))
    api(libs.kotlinx.coroutines.core)

    implementation(libs.kotlin.stdlib)

    androidTestImplementation(libs.kotlinx.coroutines.test)

    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.test.rules)
}
