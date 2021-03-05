plugins {
    id(Android.applicationPlugin)

    kotlin(Kotlin.androidPlugin)
    kotlin(Kotlin.androidExtensions)
    kotlin(Kotlin.kapt)

    id(Dependencies.App.hiltAppPlugin)
}

android {
    compileSdkVersion(Android.compileSdk)
    buildToolsVersion(Android.buildTools)

    defaultConfig {
        applicationId = Android.DefaultConfig.applicationId

        minSdkVersion(Android.DefaultConfig.minSdk)
        targetSdkVersion(Android.DefaultConfig.targetSdk)

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

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }

        kotlinOptions {
            jvmTarget = "1.8"
        }

        kapt {
            correctErrorTypes = true
        }
    }
}

dependencies {
    implementation(project(":pinkman"))
    implementation(project(":pinkman-ui"))

    implementation(Kotlin.stdLib)
    implementation(Dependencies.Common.appCompat)

    implementation(Dependencies.App.hiltAndroid)
    implementation(Dependencies.App.hiltLifecycleViewmodel)

    implementation(Dependencies.App.navigationFragmentKtx)
    implementation(Dependencies.App.navigationUiKtx)

    implementation(Dependencies.App.lifecycleViewmodelKtx)
    implementation(Dependencies.App.lifecycleLivedataKtx)

    implementation(Dependencies.App.coreKtx)
    implementation(Dependencies.App.constraintlayout)

    kapt(Dependencies.App.hiltCompiler)
    kapt(Dependencies.App.hiltAndroidCompiler)

    testImplementation(TestDependencies.junit)

    androidTestImplementation(TestDependencies.junitExt)
    androidTestImplementation(TestDependencies.espresso)
}

