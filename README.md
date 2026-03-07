> [日本語](./docs/README_ja.md)

# kmixi2

![badge][badge-jvm]
![badge][badge-ios]
![badge][badge-mac]

**This library is a [mixi2](https://mixi.social/) client library compatible
with [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html).**
Unlike other PlanetLink SDKs, mixi2 uses gRPC (Protocol Buffers) instead of REST/JSON.
All platforms (JVM, iOS, macOS) are fully supported via [GRPC-Kotlin-Multiplatform][kmpgrpc] v1.5.0.
Authentication (OAuth2) uses [khttpclient] for the HTTP POST token endpoint.

Proto definitions: https://github.com/mixigroup/mixi2-api
Go SDK reference: https://github.com/mixigroup/mixi2-application-sdk-go

### Platform Support

| Platform | gRPC API | Auth (HTTP) | Streaming | Notes |
|----------|----------|-------------|-----------|-------|
| JVM | OK | OK | OK | OkHttp transport |
| iOS | OK | OK | OK | gRPC C-core transport |
| macOS | OK | OK | OK | gRPC C-core transport |
| JS | - | OK | - | Server does not support grpc-web |

> JS platform: The mixi2 server does not support the grpc-web protocol required for browser/Node.js gRPC calls. Auth (HTTP POST) works, but gRPC API calls are not available.

## Usage

Below is how to use it with Kotlin on JVM using Gradle.
Additionally, please check the test code as well.

### Snapshot

```kotlin:build.gradle.kts
repositories {
    mavenCentral()
+   maven { url = uri("https://repo.repsy.io/mvn/uakihir0/public") }
}

dependencies {
+   implementation("work.socialhub.kmixi2:core:0.0.1-SNAPSHOT")
+   implementation("work.socialhub.kmixi2:stream:0.0.1-SNAPSHOT")
}
```

### Using as part of a regular Java project

All of the above can be added to and used in regular Java projects, too. All you have to do is to use the suffix `-jvm` when listing the dependency.

Here is a sample Maven configuration:

```xml
<dependency>
    <groupId>work.socialhub.kmixi2</groupId>
    <artifactId>core-jvm</artifactId>
    <version>[VERSION]</version>
</dependency>
```

### Authentication

mixi2 uses OAuth2 Client Credentials. Obtain an access token from the token endpoint:

```kotlin
val mixi2 = Mixi2Factory.instance(
    host = "application-api.mixi.social",
)

val response = mixi2.auth().obtainToken(
    AuthObtainTokenRequest().also {
        it.clientId = CLIENT_ID
        it.clientSecret = CLIENT_SECRET
        it.tokenEndpoint = "https://application-auth.mixi.social/oauth2/token"
    }
)

println(response.data.accessToken)
```

### Creating a Post

```kotlin
val mixi2 = Mixi2Factory.instance(
    host = "application-api.mixi.social",
    accessToken = ACCESS_TOKEN,
)

val response = mixi2.posts().createPost(
    PostsCreatePostRequest().also {
        it.text = "Hello from kmixi2!"
    }
)

println(response.data.post?.postId)
```

### Getting Users

```kotlin
val response = mixi2.users().getUsers(
    UsersGetUsersRequest().also {
        it.userIdList = arrayOf("user-id-1", "user-id-2")
    }
)

response.data.users.forEach {
    println("${it.displayName} (@${it.profileTag})")
}
```

### Stamps

```kotlin
// Get official stamps
val stamps = mixi2.stamps().getStamps(
    StampsGetStampsRequest().also {
        it.officialStampLanguage = "ja"
    }
)

// Add a stamp to a post
mixi2.stamps().addStampToPost(
    StampsAddStampToPostRequest().also {
        it.postId = "target-post-id"
        it.stampId = "stamp-id"
    }
)
```

### Chat

```kotlin
val response = mixi2.chat().sendChatMessage(
    ChatSendChatMessageRequest().also {
        it.roomId = "room-id"
        it.text = "Hello!"
    }
)

println(response.data.chatMessage?.messageId)
```

### Event Stream

```kotlin
val stream = mixi2.stream().eventStream()

stream.onEvent(object : EventStreamListener {
    override fun onPostCreated(post: Post) {
        println("New post: ${post.text}")
    }
    override fun onChatMessageReceived(chatMessage: ChatMessage) {
        println("New message: ${chatMessage.text}")
    }
})

stream.open()
```

Note: The stream module connects to `application-stream.mixi.social` for server-streaming gRPC.

## API Reference

| Resource | Methods | Description |
|----------|---------|-------------|
| `auth()` | `obtainToken` | OAuth2 client credentials token (HTTP POST) |
| `users()` | `getUsers` | Get user profiles by ID |
| `posts()` | `getPosts`, `createPost`, `initiatePostMediaUpload`, `getPostMediaStatus` | Post operations |
| `chat()` | `sendChatMessage` | Send chat messages |
| `stamps()` | `getStamps`, `addStampToPost` | Stamp (reaction) operations |
| `stream()` | `eventStream` | Server-streaming events (post created, chat message received) |

## Build

```bash
./gradlew :grpc:build     # Generate proto stubs (all platforms)
./gradlew jvmJar          # Build all JVM artifacts
./gradlew :core:jvmTest   # Run core tests
./gradlew :stream:jvmTest # Run stream tests
```

## License

MIT License

## Author

[Akihiro Urushihara](https://github.com/uakihir0)

[khttpclient]: https://github.com/uakihir0/khttpclient
[kmpgrpc]: https://github.com/TimOrtel/GRPC-Kotlin-Multiplatform
[badge-jvm]: http://img.shields.io/badge/-jvm-DB413D.svg
[badge-js]: http://img.shields.io/badge/-js-F8DB5D.svg
[badge-ios]: http://img.shields.io/badge/-ios-CDCDCD.svg
[badge-mac]: http://img.shields.io/badge/-macos-111111.svg
[badge-linux]: http://img.shields.io/badge/-linux-2D3F6C.svg
[badge-windows]: http://img.shields.io/badge/-windows-4D76CD.svg
