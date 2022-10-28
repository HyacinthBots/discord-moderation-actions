# Discord Moderator Actions

WIP Library for the Kord(Ex) Discord Libraries that provides a single DSL that will execute the moderation action, dm
the user concerned about it, log it to channel.

This project is licensed under the MIT License

## Usage instructions
#### Latest version: 
* [Jitpack](https://jitpack.io/#NoComment1105/discord-moderation-actions)
* [GitHub](https://github.com/NoComment1105/disord-moderation-actions/releases/latest)
### Adding the dependency:
#### Maven:
```xml
<!-- Adding the Jitpack repository -->
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<!-- Adding the dependency. Replace TAG with the latest version -->
<dependency>
    <groupId>com.github.nocomment1105</groupId>
    <artifactId>discord-moderation-actions</artifactId>
    <version>TAG</version>
</dependency>

```

#### Gradle (Groovy):
```groovy
repositories {
    maven {
        name = 'Jitpack'
        url = 'https://jitpack.io'
    }
}

dependencies {
    implementation('com.github.nocomment1105:discord-moderation-actions:TAG')
}
```

#### Gradle (Kotlin):
```kotlin
repositories {
    maven {
        name = "Jitpack"
        url = uri("https://jitpack.io")
    }
}

dependencies {
    implementation("com.github.nocomment1105:discord-moderation-actions:TAG")
}
```
