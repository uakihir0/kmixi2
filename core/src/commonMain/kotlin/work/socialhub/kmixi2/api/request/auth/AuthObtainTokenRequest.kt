package work.socialhub.kmixi2.api.request.auth

import kotlin.js.JsExport

@JsExport
class AuthObtainTokenRequest {
    var clientId: String? = null
    var clientSecret: String? = null
    var tokenEndpoint: String? = null
    var grantType: String = "client_credentials"
    var scope: String? = null
}
