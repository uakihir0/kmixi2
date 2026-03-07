package work.socialhub.kmixi2.internal

import work.socialhub.kmixi2.api.AuthResource
import work.socialhub.kmixi2.api.request.auth.AuthObtainTokenRequest
import work.socialhub.kmixi2.api.response.Response
import work.socialhub.kmixi2.api.response.auth.AuthObtainTokenResponse
import work.socialhub.kmixi2.util.toBlocking

class AuthResourceImpl : AuthResource {

    override suspend fun obtainToken(
        request: AuthObtainTokenRequest
    ): Response<AuthObtainTokenResponse> {
        // TODO: OAuth2 Client Credentials via khttpclient HTTP POST
        //  val response = HttpRequest()
        //      .url(request.tokenEndpoint!!)
        //      .param("grant_type", request.grantType)
        //      .param("client_id", request.clientId!!)
        //      .param("client_secret", request.clientSecret!!)
        //      .also { req -> request.scope?.let { req.param("scope", it) } }
        //      .post()
        //  return Response(json.decodeFromString(response.stringBody))
        TODO("OAuth2 implementation pending")
    }

    override fun obtainTokenBlocking(
        request: AuthObtainTokenRequest
    ): Response<AuthObtainTokenResponse> = toBlocking { obtainToken(request) }
}
