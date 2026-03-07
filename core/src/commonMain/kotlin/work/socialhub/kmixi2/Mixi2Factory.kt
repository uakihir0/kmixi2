package work.socialhub.kmixi2

import work.socialhub.kmixi2.internal.Mixi2Impl
import kotlin.js.JsExport

@JsExport
object Mixi2Factory {
    fun instance(
        host: String,
        accessToken: String = "",
        authKey: String? = null,
    ): Mixi2 {
        return Mixi2Impl(host, accessToken, authKey)
    }
}
