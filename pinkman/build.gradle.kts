plugins {
    id(Android.libraryPlugin)
    kotlin(Kotlin.androidPlugin)
    kotlin(Kotlin.androidExtensions)
}
apply(from = "../gradle/publish.gradle")

android {
    compileSdkVersion(Android.compileSdk)

    defaultConfig {
        minSdkVersion(Android.DefaultConfig.minSdk)
        targetSdkVersion(Android.DefaultConfig.targetSdk)
        versionCode = Android.DefaultConfig.versionCode
        versionName = Android.DefaultConfig.versionName

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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    packagingOptions {
        exclude("META-INF/LICENSE*")
    }
}

dependencies {
    implementation(Kotlin.stdLib)
    implementation(Dependencies.Lib.securityCrypto)
    implementation(Dependencies.Lib.argon2)

    testImplementation(TestDependencies.junit)
    testImplementation(TestDependencies.assertjCore)
    testImplementation(TestDependencies.mockitoCore)
    testImplementation(TestDependencies.mockitoKotlin)

    androidTestImplementation(TestDependencies.testRunner)
    androidTestImplementation(TestDependencies.testRules)
}
