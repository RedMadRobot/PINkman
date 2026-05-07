plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
    convention.publishing
}

description = "PINkman is a library to help implementing an authentication by a PIN code in a secure manner"

android {
    namespace = "com.redmadrobot.pinkman"

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
            excludes += setOf("META-INF/LICENSE*")
        }
    }
}

dependencies {
    implementation(libs.kotlin.stdlib)
    implementation(libs.androidx.security.crypto)
    implementation(libs.argon2kt)

    testImplementation(libs.junit)
    testImplementation(libs.assertj.core)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)

    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.test.rules)
}
