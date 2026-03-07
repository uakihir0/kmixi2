package work.socialhub.kmixi2.apis

import kotlinx.coroutines.test.runTest
import work.socialhub.kmixi2.AbstractTest
import work.socialhub.kmixi2.api.request.auth.AuthObtainTokenRequest
import kotlin.test.Test

class AuthTest : AbstractTest() {

    @Test
    fun testObtainToken() = runTest {
        val request = AuthObtainTokenRequest().also {
            it.clientId = CLIENT_ID
            it.clientSecret = CLIENT_SECRET
            it.tokenEndpoint = TOKEN_ENDPOINT
        }
        val response = mixi2().auth().obtainToken(request)
        println(response.data.accessToken)
    }
}
