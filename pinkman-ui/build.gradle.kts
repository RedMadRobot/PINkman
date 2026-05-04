plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
    convention.publishing
}

description = "PINkman-UI is a library with additional UI components for an authentication by a PIN"

android {
    namespace = "com.redmadrobot.pinkman_ui"

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
}


dependencies {
    implementation(libs.kotlin.stdlib)
    implementation(libs.androidx.appcompat)

    testImplementation(libs.junit)
}
