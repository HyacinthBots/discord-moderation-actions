pluginManagement {
    plugins {
        kotlin("jvm") version "1.7.20"
        kotlin("plugin.serialization") version "1.7.20"

        id("io.gitlab.arturbosch.detekt") version "1.21.0"

        id("com.github.jakemarsden.git-hooks") version "0.0.2"

        id("org.cadixdev.licenser") version "0.6.1"
    }
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "discord-moderation-actions"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("libs.versions.toml"))
        }
    }
}

