package work.socialhub.kmixi2.api.response.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
@Serializable
class AuthObtainTokenResponse {
    @SerialName("access_token")
    var accessToken: String = ""

    @SerialName("token_type")
    var tokenType: String = ""

    @SerialName("expires_in")
    var expiresIn: Long = 0
}
