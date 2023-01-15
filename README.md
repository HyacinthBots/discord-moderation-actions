# Discord Moderator Actions

WIP Library for the Kord(Ex) Discord Libraries that provides a single DSL that will execute the moderation action, dm
the user concerned about it, log it to channel.

This project is licensed under the MIT License

## Usage instructions

#### Latest version:

* [Maven Central](https://s01.oss.sonatype.org/content/repositories/releases/org/hyacinthbots/discord-moderation-actions)
* [GitHub](https://github.com/NoComment1105/disord-moderation-actions/releases/latest)

### Adding the dependency:

#### Maven:

```xml
<!-- Optioanlly adding the snapshot repository repository -->
<repositories>
    <repository>
        <id>Sonatype snapshots</id>
        <url>https://s01.oss.sonatype.org/content/repositories/snapshots/</url>
    </repository>
</repositories>

        <!-- Adding the dependency. Replace TAG with the latest version -->
<dependency>
<groupId>org.hyacinthbots</groupId>
<artifactId>discord-moderation-actions</artifactId>
<version>TAG</version>
</dependency>

```

#### Gradle (Groovy):

```groovy
repositories {
    mavenCentral()
    // Optionally add the snapshots repo
    maven {
        name = 'Sonatype snapshots'
        url = 'https://s01.oss.sonatype.org/content/repositories/snapshots/'
    }
}

// Adding the dependency. Replace TAG with the latest version
dependencies {
    implementation('org.hyacinthbots:discord-moderation-actions:TAG')
}
```

#### Gradle (Kotlin):

```kotlin
repositories {
    mavenCentral()
    // Optionally add the snapshots repository
    maven {
        name = "Sonatype snapshots"
        url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    }
}

// Adding the dependency. Replace TAG with the latest version
dependencies {
    implementation("org.hyacinthbots:discord-moderation-actions:TAG")
}
```

### Getting started

Once you have added the dependency to your project, you're fully ready to start using Discord Moderation Actions (DMA).
There is no specific configuration required for DMA, as all configuration for actions is done within the chosen action
DSL.

**NOTE: This project requires [KordEx](https://github.com/Kord-Extensions/kord-extensions) currently. Maybe in the
future support for vanilla [Kord](https://github.com/kordlib/Kord) will be added**

Examples and full documentation can be found in
the [Wiki](https://github.com/HyacinthBots/discord-moderation-actions/wiki)

## Reporting Issues and Requesting features

If you have any issues to report or features to request, please open an issue on
the [GitHub Issue Tracker](https://github.com/NoComment1105/discord-moderation-actions/issues). I request that you 
follow the template provided and provide as much information as possible. Issues that don't follow the template will 
simply be closed and ignored.
