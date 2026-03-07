package work.socialhub.kmixi2.api

import work.socialhub.kmixi2.api.request.auth.AuthObtainTokenRequest
import work.socialhub.kmixi2.api.response.Response
import work.socialhub.kmixi2.api.response.auth.AuthObtainTokenResponse
import kotlin.js.JsExport

@JsExport
interface AuthResource {

    suspend fun obtainToken(
        request: AuthObtainTokenRequest
    ): Response<AuthObtainTokenResponse>

    @JsExport.Ignore
    fun obtainTokenBlocking(
        request: AuthObtainTokenRequest
    ): Response<AuthObtainTokenResponse>
}
