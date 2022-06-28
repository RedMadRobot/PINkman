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
    implementation("com.android.tools.build:gradle:4.1.2")
}
