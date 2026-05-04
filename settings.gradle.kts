pluginManagement {
    repositories {
        google {
            content {
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
                includeGroupAndSubgroups("androidx")
            }
        }
        gradlePluginPortal()
    }
}

include (":pinkman-rx3")
include (":pinkman-coroutines")
include (":pinkman-ui")
include (":pinkman")
include (":app")
rootProject.name = "PINkman"
