package work.socialhub.kmixi2

import kotlin.js.JsExport

@JsExport
class Mixi2Auth {
    var clientId: String = ""
    var clientSecret: String = ""
    var tokenEndpoint: String = ""
    var accessToken: String? = null
    var authKey: String? = null
}
