plugins {
    `kotlin-dsl`
}

group = "com.redmadrobot.build"

repositories {
    google()
    mavenCentral()
}

gradlePlugin {
    plugins {
        create("PublishPlugin") {
            id = "publishPlugin"
            implementationClass = "com.redmadrobot.build.PublishPlugin"
        }
    }
}

dependencies {
    implementation("com.android.tools.build:gradle:8.11.1")
}

configurations.all {
    resolutionStrategy {
        // Force the version Hilt needs to run its tasks
        force("com.squareup:javapoet:1.13.0")
    }
}
