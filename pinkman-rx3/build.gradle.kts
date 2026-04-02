plugins {
    id(Android.libraryPlugin)
    id(Kotlin.androidPlugin)
    id("maven-publish")
    id("publishPlugin")
}

android {
    namespace = "com.redmadrobot.pinkman_rx3"

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(21)
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
    api(Dependencies.Lib.rxJava3)

    implementation(Kotlin.stdLib)

    androidTestImplementation(TestDependencies.testRunner)
    androidTestImplementation(TestDependencies.testRules)
}
