import com.redmadrobot.build.dsl.*
import com.vanniktech.maven.publish.SonatypeHost

plugins {
    id("com.vanniktech.maven.publish")
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL, automaticRelease = true)
    signAllPublications()

    pom {
        name.convention(project.provider { project.name })
        description.convention(project.provider { project.description })

        licenses {
            mit()
        }

        developers {
            developer {
                id.set("Fi5t")
                name.set("Artem Kulakov")
                email.set("ak@redmadrobot.com")
            }
        }

        setGitHubProject("RedMadRobot/PINkman")
    }
}

publishing {
    repositories {
        if (isRunningOnCi) githubPackages("RedMadRobot/PINkman")
    }
}
