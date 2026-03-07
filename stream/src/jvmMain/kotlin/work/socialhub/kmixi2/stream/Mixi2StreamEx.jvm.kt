package work.socialhub.kmixi2.stream

import work.socialhub.kmixi2.Mixi2
import work.socialhub.kmixi2.stream.internal.StreamResourceImpl

actual fun Mixi2.stream(): StreamResource {
    return StreamResourceImpl(
        host = host(),
        accessToken = "", // TODO: Extract from Mixi2 instance
        authKey = null,
    )
}
