# kmixi2

![badge][badge-jvm]
![badge][badge-ios]
![badge][badge-mac]
![badge][badge-linux]
![badge][badge-windows]

**このライブラリは [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html) に対応した [mixi2](https://mixi.social/) クライアントライブラリです。**
PlanetLink の他の SDK と異なり、mixi2 は REST/JSON ではなく gRPC (Protocol Buffers) を使用しています。
全プラットフォーム (JVM, iOS, macOS, Linux, Windows) は [GRPC-Kotlin-Multiplatform][kmpgrpc] v1.5.0 を使用して完全に実装されています。
認証 (OAuth2) は HTTP POST トークンエンドポイント用に [khttpclient] を使用しています。

Proto 定義: https://github.com/mixigroup/mixi2-api
Go SDK リファレンス: https://github.com/mixigroup/mixi2-application-sdk-go

### プラットフォーム対応状況

| プラットフォーム | gRPC API | 認証 (HTTP) | ストリーミング | 備考 |
|-----------------|----------|-------------|--------------|------|
| JVM | OK | OK | OK | OkHttp トランスポート |
| iOS | OK | OK | OK | gRPC C-core トランスポート |
| macOS | OK | OK | OK | gRPC C-core トランスポート |
| Linux | OK | OK | OK | gRPC C-core トランスポート |
| Windows | OK | OK | OK | gRPC C-core トランスポート |
| JS | - | OK | - | サーバーが grpc-web 未対応 |

> JS プラットフォーム: mixi2 サーバーはブラウザ/Node.js の gRPC 呼び出しに必要な grpc-web プロトコルに対応していません。認証 (HTTP POST) は動作しますが、gRPC API 呼び出しは利用できません。

## 使い方

以下は JVM プラットフォームにおいて Gradle を用いて Kotlin で使用する際の使い方になります。
また、テストコードも合わせて確認してください。

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

### 通常の Java プロジェクトで使用する場合

上記の依存関係は通常の Java プロジェクトでも使用できます。依存関係の指定時にサフィックス `-jvm` を追加してください。

Maven の設定例:

```xml
<dependency>
    <groupId>work.socialhub.kmixi2</groupId>
    <artifactId>core-jvm</artifactId>
    <version>[VERSION]</version>
</dependency>
```

### 認証

mixi2 は OAuth2 Client Credentials を使用します。トークンエンドポイントからアクセストークンを取得します:

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

### 投稿の作成

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

### ユーザー情報の取得

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

### スタンプ

```kotlin
// 公式スタンプの取得
val stamps = mixi2.stamps().getStamps(
    StampsGetStampsRequest().also {
        it.officialStampLanguage = "ja"
    }
)

// 投稿にスタンプを追加
mixi2.stamps().addStampToPost(
    StampsAddStampToPostRequest().also {
        it.postId = "target-post-id"
        it.stampId = "stamp-id"
    }
)
```

### チャット

```kotlin
val response = mixi2.chat().sendChatMessage(
    ChatSendChatMessageRequest().also {
        it.roomId = "room-id"
        it.text = "Hello!"
    }
)

println(response.data.chatMessage?.messageId)
```

### イベントストリーム

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

注意: stream モジュールはサーバーストリーミング gRPC のために `application-stream.mixi.social` に接続します。

## API リファレンス

| リソース | メソッド | 説明 |
|---------|---------|------|
| `auth()` | `obtainToken` | OAuth2 クライアントクレデンシャルトークン (HTTP POST) |
| `users()` | `getUsers` | ID によるユーザープロフィール取得 |
| `posts()` | `getPosts`, `createPost`, `initiatePostMediaUpload`, `getPostMediaStatus` | 投稿操作 |
| `chat()` | `sendChatMessage` | チャットメッセージ送信 |
| `stamps()` | `getStamps`, `addStampToPost` | スタンプ（リアクション）操作 |
| `stream()` | `eventStream` | サーバーストリーミングイベント (投稿作成、チャットメッセージ受信) |

## ビルド

```bash
./gradlew :grpc:build     # Proto スタブの生成 (全プラットフォーム)
./gradlew jvmJar          # 全 JVM アーティファクトのビルド
./gradlew :core:jvmTest   # core テストの実行
./gradlew :stream:jvmTest # stream テストの実行
```

## ライセンス

MIT License

## 作者

[Akihiro Urushihara](https://github.com/uakihir0)

[khttpclient]: https://github.com/uakihir0/khttpclient
[kmpgrpc]: https://github.com/TimOrtel/GRPC-Kotlin-Multiplatform
[badge-jvm]: http://img.shields.io/badge/-jvm-DB413D.svg
[badge-js]: http://img.shields.io/badge/-js-F8DB5D.svg
[badge-ios]: http://img.shields.io/badge/-ios-CDCDCD.svg
[badge-mac]: http://img.shields.io/badge/-macos-111111.svg
[badge-linux]: http://img.shields.io/badge/-linux-2D3F6C.svg
[badge-windows]: http://img.shields.io/badge/-windows-4D76CD.svg
