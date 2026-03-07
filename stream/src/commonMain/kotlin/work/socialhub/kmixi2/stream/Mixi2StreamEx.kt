package work.socialhub.kmixi2.stream

import work.socialhub.kmixi2.Mixi2
import work.socialhub.kmixi2.stream.internal.StreamResourceImpl

fun Mixi2.stream(): StreamResource {
    return StreamResourceImpl(
        host = host(),
        accessToken = accessToken(),
        authKey = authKey(),
    )
}
