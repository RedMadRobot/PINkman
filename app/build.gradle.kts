plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
    alias(libs.plugins.hilt)
}

kapt {
    correctErrorTypes = true
}

android {
    namespace = "com.redmadrobot.sample"

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        viewBinding = true
    }

    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(17)
        }
    }

    compileSdkVersion(Android.compileSdk)
    buildToolsVersion(Android.buildTools)

    defaultConfig {
        applicationId = Android.DefaultConfig.applicationId

        minSdk = Android.DefaultConfig.minSdk
        targetSdk = Android.DefaultConfig.targetSdk

        testInstrumentationRunner = Android.DefaultConfig.instrumentationRunner


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
}

dependencies {
    implementation(project(":pinkman"))
    implementation(project(":pinkman-ui"))

    implementation(libs.kotlin.stdlib)
    implementation(libs.androidx.appcompat)

    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.constraintlayout)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
}
