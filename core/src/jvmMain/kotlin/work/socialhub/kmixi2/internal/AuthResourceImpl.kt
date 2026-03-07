package work.socialhub.kmixi2.internal

import kotlinx.serialization.json.Json
import work.socialhub.kmixi2.Mixi2Exception
import work.socialhub.kmixi2.api.AuthResource
import work.socialhub.kmixi2.api.request.auth.AuthObtainTokenRequest
import work.socialhub.kmixi2.api.response.Response
import work.socialhub.kmixi2.api.response.auth.AuthObtainTokenResponse
import work.socialhub.kmixi2.util.toBlocking
import work.socialhub.khttpclient.HttpRequest

class AuthResourceImpl : AuthResource {

    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun obtainToken(
        request: AuthObtainTokenRequest
    ): Response<AuthObtainTokenResponse> {
        try {
            val httpResponse = HttpRequest()
                .url(request.tokenEndpoint!!)
                .param("grant_type", request.grantType)
                .param("client_id", request.clientId!!)
                .param("client_secret", request.clientSecret!!)
                .also { req -> request.scope?.let { req.param("scope", it) } }
                .post()

            val body = httpResponse.stringBody
            if (httpResponse.status != 200) {
                throw Mixi2Exception(httpResponse.status, body)
            }
            val data = json.decodeFromString<AuthObtainTokenResponse>(body)
            return Response(data).also { it.json = body }
        } catch (e: Mixi2Exception) {
            throw e
        } catch (e: Exception) {
            throw Mixi2Exception(e)
        }
    }

    override fun obtainTokenBlocking(
        request: AuthObtainTokenRequest
    ): Response<AuthObtainTokenResponse> = toBlocking { obtainToken(request) }
}
