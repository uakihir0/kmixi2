package work.socialhub.kmixi2

import work.socialhub.kmixi2.internal.Mixi2Impl

actual object Mixi2Factory {
    actual fun instance(
        host: String,
        accessToken: String,
        authKey: String?,
    ): Mixi2 {
        return Mixi2Impl(host, accessToken, authKey)
    }
}
