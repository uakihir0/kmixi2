package work.socialhub.kmixi2

// FIXME: Replace with KMP gRPC library when available.
actual object Mixi2Factory {
    actual fun instance(
        host: String,
        accessToken: String,
        authKey: String?,
    ): Mixi2 {
        throw UnsupportedOperationException(
            "kmixi2 is currently JVM-only. KMP gRPC support pending."
        )
    }
}
