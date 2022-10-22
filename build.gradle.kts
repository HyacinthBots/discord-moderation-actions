import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("org.jetbrains.kotlinx.binary-compatibility-validator") version "0.11.1"
    id("io.gitlab.arturbosch.detekt")
    id("com.github.jakemarsden.git-hooks")
    id("org.cadixdev.licenser")
    `maven-publish`
}

group = "io.github.nocomment1105"
version = "0.1.0"
val javaVersion = 17

repositories {
    mavenCentral()

    maven {
        name = "Kotlin Discord"
        url = uri("https://maven.kotlindiscord.com/repository/maven-public/")
    }

    maven {
        name = "Sonatype Snapshots"
        url = uri("https://oss.sonatype.org/content/repositories/snapshots")
    }
}

dependencies {
    detektPlugins(libs.detekt)
    implementation(libs.kotlin.stdlib)
    implementation(libs.kordex)
}

gitHooks {
    setHooks(
        mapOf("pre-commit" to "updateLicenses detekt")
    )
}

kotlin {
    explicitApi()
    jvmToolchain(javaVersion)
}

tasks {
    wrapper {
        gradleVersion = "7.5.1"
        distributionType = Wrapper.DistributionType.ALL
    }

    withType<Test> {
        useJUnitPlatform()
    }

    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = javaVersion.toString()
            languageVersion = "1.7"
            incremental = true
            freeCompilerArgs = freeCompilerArgs + listOf(
                "-opt-in=kotlin.RequiresOptIn"
            )
        }
    }

    publishing {
        repositories {
            mavenCentral()
        }
        publications {
            create<MavenPublication>("maven") {
                artifact(kotlinSourcesJar)
            }
        }
    }
}

detekt {
    buildUponDefaultConfig = true
    config = files("$rootDir/detekt.yml")

    autoCorrect = true
}

license {
    setHeader(rootProject.file("HEADER"))
    include("**/*.kt")
}


