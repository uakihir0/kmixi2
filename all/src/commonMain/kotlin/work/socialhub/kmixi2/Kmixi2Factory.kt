package work.socialhub.kmixi2

import kotlin.js.JsExport

@JsExport
object Kmixi2Factory {
    fun instance(
        host: String,
        accessToken: String = "",
        authKey: String? = null,
    ) = Mixi2Factory.instance(host, accessToken, authKey)
}
