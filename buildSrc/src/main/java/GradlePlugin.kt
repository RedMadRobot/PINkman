object GradlePlugin {
    object DependencyCheck {
        const val version = "6.1.5"
        const val plugin = "org.owasp.dependencycheck"
    }

    object DependencyVersions {
        const val version = "0.38.0"
        const val plugin = "com.github.ben-manes.versions"
    }

    object Detekt {
        const val version = "1.17.1"
        const val plugin = "io.gitlab.arturbosch.detekt"

        const val formattingPlugin = "io.gitlab.arturbosch.detekt:detekt-formatting:$version"
    }

    object Bintray {
        const val gradlePlugin = "com.jfrog.bintray.gradle:gradle-bintray-plugin:1.8.5"
    }

}
