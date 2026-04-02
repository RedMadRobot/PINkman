plugins {
    id(Android.applicationPlugin)

    id(Kotlin.androidPlugin)
    id(Kotlin.kapt)

    id(Dependencies.App.hiltAppPlugin)
}

kapt {
    correctErrorTypes = true
}

android {
    namespace = "com.redmadrobot.sample"

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    buildFeatures {
        viewBinding = true
    }

    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(21)
        }
    }

    compileSdkVersion(Android.compileSdk)
    buildToolsVersion(Android.buildTools)

    defaultConfig {
        applicationId = Android.DefaultConfig.applicationId

        minSdk = Android.DefaultConfig.minSdk
        targetSdk = Android.DefaultConfig.targetSdk

        versionCode = Android.DefaultConfig.versionCode
        versionName = Android.DefaultConfig.versionName

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

    implementation(Kotlin.stdLib)
    implementation(Dependencies.Common.appCompat)

    implementation(Dependencies.App.hiltAndroid)
    kapt(Dependencies.App.hiltAndroidCompiler)

    implementation(Dependencies.App.navigationFragmentKtx)
    implementation(Dependencies.App.navigationUiKtx)

    implementation(Dependencies.App.lifecycleViewmodelKtx)
    implementation(Dependencies.App.lifecycleLivedataKtx)

    implementation(Dependencies.App.coreKtx)
    implementation(Dependencies.App.constraintlayout)

    testImplementation(TestDependencies.junit)

    androidTestImplementation(TestDependencies.junitExt)
    androidTestImplementation(TestDependencies.espresso)
}
