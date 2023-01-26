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
    implementation("com.android.tools.build:gradle:7.4.0")
    implementation("com.squareup:javapoet:1.13.0")
}
