pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        jcenter()

        maven { url = uri("https://jitpack.io") }

    }
}
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        jcenter()
        maven { url = uri("https://jitpack.io") }

    }
}

rootProject.name = "BudgetBee"
include(":app")
