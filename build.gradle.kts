plugins {
    id("com.android.application") apply false
    id("com.android.library") apply false
    id("org.jetbrains.kotlin.android") apply false
    id("org.jetbrains.kotlin.kapt") apply false
    alias(libs.plugins.hilt) apply false
    id("io.gitlab.arturbosch.detekt")
    alias(libs.plugins.dependency.versions)
    alias(libs.plugins.dependency.check)
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

val detektFormatting = libs.detekt.formatting

subprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")

    detekt {
        config = rootProject.files("detekt/config.yml")
        baseline = rootProject.file("detekt/detekt-baseline.xml")

        buildUponDefaultConfig = true
        allRules = true
        autoCorrect = true
        parallel = true

        reports {
            html.enabled = true
        }
    }

    dependencies {
        detektPlugins(detektFormatting)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}
