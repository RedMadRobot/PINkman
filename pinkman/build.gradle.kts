plugins {
    id(Android.libraryPlugin)
    id(Kotlin.androidPlugin)
    id("maven-publish")
    id("publishPlugin")
}

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
