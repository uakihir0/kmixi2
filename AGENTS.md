# Agent Documentation

## Overview

This repository is a mixi2 API client library for Kotlin Multiplatform. mixi2 uses gRPC (Protocol Buffers) for its API, unlike the other SDKs in PlanetLink which use REST/JSON. JVM, iOS, and macOS platforms are fully supported via [GRPC-Kotlin-Multiplatform](https://github.com/TimOrtel/GRPC-Kotlin-Multiplatform) v1.5.0. JS platform is limited to Auth (HTTP POST) only — the mixi2 server does not support grpc-web.

## Key Concepts

### mixi2 API Structure

mixi2 exposes its API via gRPC (HTTP/2 + Protocol Buffers). There are no REST endpoints.

- **Application API** (8 RPCs): GetUsers, GetPosts, CreatePost, InitiatePostMediaUpload, GetPostMediaStatus, SendChatMessage, GetStamps, AddStampToPost
- **Application Stream** (1 RPC): SubscribeEvents (server-streaming)
- **Webhook**: Client Endpoint with Ed25519 signature verification

Proto definitions: https://github.com/mixigroup/mixi2-api
Go SDK reference: https://github.com/mixigroup/mixi2-application-sdk-go

### Authentication

mixi2 uses OAuth2 Client Credentials:

1. Obtain an access token via HTTP POST to the token endpoint (`client_id`, `client_secret`, `grant_type=client_credentials`)
2. Use the access token as a Bearer token in gRPC metadata
3. Optionally include an `x-auth-key` header

The token endpoint is standard HTTP POST (not gRPC), so `AuthResource` uses khttpclient.

### Resource Grouping

8 RPC methods are organized into 4 resources + 1 auth resource:

| Resource | Methods | Proto RPC |
|----------|---------|-----------|
| `UsersResource` | `getUsers` | `GetUsers` |
| `PostsResource` | `getPosts`, `createPost`, `initiatePostMediaUpload`, `getPostMediaStatus` | `GetPosts`, `CreatePost`, `InitiatePostMediaUpload`, `GetPostMediaStatus` |
| `ChatResource` | `sendChatMessage` | `SendChatMessage` |
| `StampsResource` | `getStamps`, `addStampToPost` | `GetStamps`, `AddStampToPost` |
| `AuthResource` | `obtainToken` | OAuth2 token endpoint (HTTP POST) |

### Streaming API

Server-streaming gRPC via `SubscribeEvents`:

Event types:
- `PING` - Keep-alive
- `POST_CREATED` - New post in subscribed community
- `CHAT_MESSAGE_RECEIVED` - New chat message

## Directory Structure

- **`grpc/`**: KMP gRPC module for Protocol Buffer code generation (all platforms)
  - `src/main/proto/` - Protocol Buffer definitions
  - Generated multiplatform Kotlin stubs via KMP gRPC plugin
- **`core/`**: gRPC API client library
  - `api/` - Resource interfaces
    - `request/` - Request objects (grouped by resource)
    - `response/` - Response objects (grouped by resource)
  - `entity/` - Data models (User, Post, ChatMessage, etc.)
    - `constant/` - Enum types matching proto enums
  - `internal/` - Multiplatform implementations (gRPC stubs, ProtoMapper)
  - `util/` - Utilities (BlockingUtil, Headers)
- **`stream/`**: Streaming API (gRPC server-streaming)
- **`all/`**: Package containing all modules (for platform distribution)
- **`plugins/`**: Gradle build configuration

## Build

Proto code generation is handled by the `grpc/` module using the KMP gRPC plugin (`io.github.timortel.kmpgrpc.plugin`). The `core/` and `stream/` modules depend on `grpc/` via `project(":grpc")`.

```shell
./gradlew :grpc:build     # Generate proto stubs (all platforms)
./gradlew jvmJar          # Build all JVM artifacts
./gradlew :core:jvmTest   # Run core tests
./gradlew :stream:jvmTest # Run stream tests
```

### gRPC Module Notes

- Proto package `social.mixi.application.constant.v1` (renamed from `const` to avoid Kotlin reserved word).
- Generated classes use `expect class` with platform-specific `actual` implementations.
- Properties use snake_case (matching proto field names): `user_id`, `post_media_listList`.
- Optional fields: non-null property + `isXxxSet: Boolean` check (e.g., `isUser_avatarSet`).
- Oneof fields: sealed class hierarchy (e.g., `PostMedia.Content.Image`, `PostMedia.Content.Video`).
- Metadata is passed per-call: `stub.GetUsers(request, metadata)` — no interceptor pattern.

## Testing

Tests are in `commonTest` and run on JVM and Native (macOS). JS tests are stub-only (gRPC not available).

Run all core tests:

```shell
./gradlew :core:jvmTest                    # JVM
./gradlew :core:macosArm64Test             # macOS Native
```

Run specific tests:

```shell
./gradlew :core:jvmTest --tests "work.socialhub.kmixi2.apis.UsersTest"
./gradlew :core:jvmTest --tests "work.socialhub.kmixi2.apis.AuthTest"
```

If network access is not available, verify successful build:

```shell
./gradlew jvmJar
```

If authentication credentials are required for tests, create `secrets.json` based on `secrets.json.default`.

Test infrastructure uses `expect`/`actual` for platform-specific operations:
- `commonTest`: `TestPlatform.kt` (expect declarations for `getEnvVar`, `readFileText`)
- `jvmTest`: `TestPlatform.jvm.kt` (System.getenv + java.io.File)
- `nativeTest`: `TestPlatform.native.kt` (posix getenv + fopen/fgets)
- `jsTest`: `TestPlatform.js.kt` (stub returning null — tests will skip on JS)

## Implementation Guidelines

### Transport Layer

- **JVM/iOS/macOS**: KMP gRPC v1.5.0 (`io.github.timortel:kmp-grpc-core`) — JVM (OkHttp), Native (gRPC C core)
- **JS**: gRPC API not available (server does not support grpc-web). Auth (HTTP POST) works via khttpclient
- **Auth (OAuth2)**: Uses khttpclient (standard HTTP POST, not gRPC)

### Implementation Architecture (commonMain)

- `AbstractResourceImpl` - Base class providing auth metadata builder (`Bearer` + `x-auth-key`) and `proceed {}` error wrapper
- `ProtoMapper.kt` - Extension functions converting KMP gRPC generated classes to SDK entity classes
- `Mixi2Impl` - Creates a shared KMP gRPC `Channel` and `ApplicationServiceStub`, injects stub into all resource implementations
- `EventStreamImpl` - Creates its own channel and collects `SubscribeEvents` server-streaming `Flow`

### Steps to Add a New API

1. If adding a new RPC, update the proto files in `grpc/src/main/proto/`.
2. Run `./gradlew :grpc:build` to regenerate stubs.
3. Add request/response models in `core/src/commonMain/kotlin/work/socialhub/kmixi2/api/request/` and `.../response/`.
4. Add the method to the appropriate resource interface in `api/`.
5. Add proto-to-entity mapping in `ProtoMapper.kt` (commonMain).
6. Update internal implementations under `internal/` (commonMain).
7. Add or update tests in `core/src/commonTest/kotlin/`.

### Naming Conventions

| Type | Naming Pattern | Example |
|------|---------------|---------|
| Request | `{Resource}{Action}Request` | `PostsCreatePostRequest` |
| Response | `{Resource}{Action}Response` | `PostsCreatePostResponse` |
| Resource | `{Category}Resource` | `PostsResource` |
| Entity | Singular form | `User`, `Post`, `ChatMessage` |
| Enum | Proto enum name mapped to Kotlin | `PostVisibility`, `EventType` |

### Entity Models

Models use `kotlinx.serialization` in commonMain. Enum constants mirror proto enum values as strings (proto enum `.name` property).

## Key File References

| Purpose | File Path |
|---------|-----------|
| Main client interface | `core/src/commonMain/kotlin/work/socialhub/kmixi2/Mixi2.kt` |
| Factory | `core/src/commonMain/kotlin/work/socialhub/kmixi2/Mixi2Factory.kt` |
| Resource interfaces | `core/src/commonMain/kotlin/work/socialhub/kmixi2/api/` |
| Request/response models | `core/src/commonMain/kotlin/work/socialhub/kmixi2/api/request/` and `.../response/` |
| Entity models | `core/src/commonMain/kotlin/work/socialhub/kmixi2/entity/` |
| Proto definitions | `grpc/src/main/proto/social/mixi/application/` |
| gRPC build config | `grpc/build.gradle.kts` |
| Implementations | `core/src/commonMain/kotlin/work/socialhub/kmixi2/internal/` |
| Proto-to-entity mapper | `core/src/commonMain/kotlin/work/socialhub/kmixi2/internal/ProtoMapper.kt` |
| Streaming API | `stream/src/commonMain/kotlin/work/socialhub/kmixi2/stream/` |
| Stream impl | `stream/src/commonMain/kotlin/work/socialhub/kmixi2/stream/internal/` |
| Test base | `core/src/commonTest/kotlin/work/socialhub/kmixi2/AbstractTest.kt` |
