import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-library`
    `maven-publish`
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("org.jetbrains.kotlinx.binary-compatibility-validator") version "0.11.1"
    id("io.gitlab.arturbosch.detekt")
    id("com.github.jakemarsden.git-hooks")
    id("org.cadixdev.licenser")
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

    implementation(libs.logback)
    implementation(libs.logging)
}

gitHooks {
    setHooks(
        mapOf("pre-commit" to "updateLicenses apiCheck detekt")
    )
}

kotlin {
    explicitApi()
    jvmToolchain(javaVersion)
}

val sourceJar = task("sourceJar", Jar::class) {
    dependsOn(tasks["classes"])
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

val javadocJar = task("javadocJar", Jar::class) {
    archiveClassifier.set("javadoc")
    from(tasks.javadoc)
    from(tasks.javadoc)
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

publishing {
    publications {
        create<MavenPublication>("publishToMavenLocal") {
            from(components.getByName("java"))
            artifact(javadocJar)
            artifact(sourceJar)
        }
    }
    repositories {  }
}

