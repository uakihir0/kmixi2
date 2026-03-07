package work.socialhub.kmixi2

import kotlin.js.JsExport

// FIXME: Replace with KMP gRPC library when available.
//  Currently only JVM is supported via grpc-kotlin.
//  JS/Native platforms will throw UnsupportedOperationException.
@JsExport
expect object Mixi2Factory {
    fun instance(
        host: String,
        accessToken: String = "",
        authKey: String? = null,
    ): Mixi2
}
