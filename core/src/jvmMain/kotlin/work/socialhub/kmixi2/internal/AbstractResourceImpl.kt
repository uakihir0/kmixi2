package work.socialhub.kmixi2.internal

// FIXME: Replace with KMP gRPC library when available.
//  Currently this base class provides gRPC channel management for JVM only.
abstract class AbstractResourceImpl(
    val host: String,
    val accessToken: String,
    val authKey: String? = null,
) {
    // TODO: Initialize gRPC ManagedChannel
    // TODO: Provide proto stub instances for RPC calls
    // TODO: Wrap gRPC StatusException -> Mixi2Exception
    // TODO: Convert proto response -> SDK entity models
}
