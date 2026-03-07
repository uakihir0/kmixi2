package work.socialhub.kmixi2.stream

import work.socialhub.kmixi2.Mixi2

// FIXME: Replace with KMP gRPC library when available.
actual fun Mixi2.stream(): StreamResource {
    throw UnsupportedOperationException(
        "kmixi2 streaming is currently JVM-only. KMP gRPC support pending."
    )
}
