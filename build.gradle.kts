// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath(Android.gradlePlugin)
        classpath(Kotlin.gradlePlugin)

        classpath(GradlePlugin.Bintray.gradlePlugin)

        classpath(Dependencies.App.hiltGradlePlugin)
    }
}

plugins {
    id(GradlePlugin.Detekt.plugin) version GradlePlugin.Detekt.version
    id(GradlePlugin.DependencyVersions.plugin) version GradlePlugin.DependencyVersions.version
    id(GradlePlugin.DependencyCheck.plugin) version GradlePlugin.DependencyCheck.version
}


allprojects {
    repositories {
        google()
        jcenter()
        maven { url = uri("https://jitpack.io") }
    }
}

subprojects {
    apply(plugin = GradlePlugin.Detekt.plugin)

    tasks {
        withType<io.gitlab.arturbosch.detekt.Detekt> {
            this.jvmTarget = "1.8"
        }
    }

    detekt {
        config = rootProject.files("detekt/config.yml")
        baseline = rootProject.file("detekt/detekt-baseline.xml")

        failFast = true
        autoCorrect = true
        parallel = true

        reports {
            html.enabled = true
        }
    }

    dependencies {
        detektPlugins(GradlePlugin.Detekt.formattingPlugin)

    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
