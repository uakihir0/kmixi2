# Agent Documentation

## Overview

This repository is a mixi2 API client library for Kotlin Multiplatform. mixi2 uses gRPC (Protocol Buffers) for its API, unlike the other SDKs in PlanetLink which use REST/JSON. The JVM platform is implemented first using grpc-kotlin; JS and Native platforms are stubs pending a KMP gRPC library.

**FIXME**: JS/Native implementations are stubs. A separate KMP gRPC library needs to be created and integrated to enable multiplatform support.

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

- **`core/`**: gRPC API client library
  - `src/main/proto/` - Protocol Buffer definitions
  - `api/` - Resource interfaces
    - `request/` - Request objects (grouped by resource)
    - `response/` - Response objects (grouped by resource)
  - `entity/` - Data models (User, Post, ChatMessage, etc.)
    - `constant/` - Enum types matching proto enums
  - `internal/` - JVM implementations (gRPC stubs, TODO)
  - `util/` - Utilities (BlockingUtil, Headers)
- **`stream/`**: Streaming API (gRPC server-streaming)
- **`all/`**: Package containing all modules (for platform distribution)
- **`plugins/`**: Gradle build configuration

## Testing

Run all core tests:

```shell
./gradlew :core:jvmTest
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

## Implementation Guidelines

### Transport Layer

- **JVM**: grpc-kotlin (`io.grpc:grpc-kotlin-stub`) + grpc-netty-shaded + protobuf-kotlin
- **JS/Native**: Stubs that throw `UnsupportedOperationException` (FIXME: awaiting KMP gRPC library)
- **Auth (OAuth2)**: Uses khttpclient (standard HTTP POST, not gRPC)

### Proto to Code Generation

Proto files in `core/src/main/proto/` are compiled by the protobuf Gradle plugin:

```shell
./gradlew generateProto
```

Generated Kotlin code is used by JVM internal implementations to make gRPC calls.

### Steps to Add a New API

1. If adding a new RPC, update the proto files in `core/src/main/proto/`.
2. Add request/response models in `core/src/commonMain/kotlin/work/socialhub/kmixi2/api/request/` and `.../response/`.
3. Add the method to the appropriate resource interface in `api/`.
4. Update internal implementations under `internal/` (JVM).
5. Add or update tests in `core/src/jvmTest/kotlin/`.

### Naming Conventions

| Type | Naming Pattern | Example |
|------|---------------|---------|
| Request | `{Resource}{Action}Request` | `PostsCreatePostRequest` |
| Response | `{Resource}{Action}Response` | `PostsCreatePostResponse` |
| Resource | `{Category}Resource` | `PostsResource` |
| Entity | Singular form | `User`, `Post`, `ChatMessage` |
| Enum | Proto enum name mapped to Kotlin | `PostVisibility`, `EventType` |

### Entity Models

Models use `kotlinx.serialization` in commonMain. Enum constants mirror proto enum values with a `from(value: String)` companion factory method.

## Key File References

| Purpose | File Path |
|---------|-----------|
| Main client interface | `core/src/commonMain/kotlin/work/socialhub/kmixi2/Mixi2.kt` |
| Factory | `core/src/commonMain/kotlin/work/socialhub/kmixi2/Mixi2Factory.kt` |
| Resource interfaces | `core/src/commonMain/kotlin/work/socialhub/kmixi2/api/` |
| Request/response models | `core/src/commonMain/kotlin/work/socialhub/kmixi2/api/request/` and `.../response/` |
| Entity models | `core/src/commonMain/kotlin/work/socialhub/kmixi2/entity/` |
| Proto definitions | `core/src/main/proto/social/mixi/application/` |
| JVM implementations | `core/src/jvmMain/kotlin/work/socialhub/kmixi2/internal/` |
| Streaming API | `stream/src/commonMain/kotlin/work/socialhub/kmixi2/stream/` |
| Test base | `core/src/jvmTest/kotlin/work/socialhub/kmixi2/AbstractTest.kt` |
