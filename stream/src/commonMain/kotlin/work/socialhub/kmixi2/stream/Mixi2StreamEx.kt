package work.socialhub.kmixi2.stream

import work.socialhub.kmixi2.Mixi2

// FIXME: Replace with KMP gRPC library when available.
//  Currently only JVM is supported.
expect fun Mixi2.stream(): StreamResource
