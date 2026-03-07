package work.socialhub.kmixi2

import kotlinx.serialization.json.Json
import work.socialhub.kmixi2.api.request.auth.AuthObtainTokenRequest
import kotlin.test.BeforeTest

open class AbstractTest {

    companion object {
        var HOST: String? = null
        var CLIENT_ID: String? = null
        var CLIENT_SECRET: String? = null
        var TOKEN_ENDPOINT: String? = null
        var AUTH_KEY: String? = null
        var ACCESS_TOKEN: String? = null
        var STREAM_HOST: String? = null
        var TEST_ROOM_ID: String? = null
    }

    protected val json = Json {
        ignoreUnknownKeys = true
    }

    suspend fun mixi2(): Mixi2 {
        ensureToken()
        return Mixi2Factory.instance(
            HOST!!,
            ACCESS_TOKEN ?: "",
            AUTH_KEY,
        )
    }

    @BeforeTest
    fun setupTest() {
        try {
            HOST = getEnvVar("MIXI2_HOST")
            CLIENT_ID = getEnvVar("MIXI2_CLIENT_ID")
            CLIENT_SECRET = getEnvVar("MIXI2_CLIENT_SECRET")
            TOKEN_ENDPOINT = getEnvVar("MIXI2_TOKEN_ENDPOINT")
            AUTH_KEY = getEnvVar("MIXI2_AUTH_KEY")
            ACCESS_TOKEN = getEnvVar("MIXI2_ACCESS_TOKEN")
            STREAM_HOST = getEnvVar("MIXI2_STREAM_HOST")
            TEST_ROOM_ID = getEnvVar("MIXI2_TEST_ROOM_ID")
        } catch (_: Exception) {
        }

        if (HOST == null) {
            readTestProps()?.get("mixi2")?.let {
                HOST = it["MIXI2_HOST"]
                CLIENT_ID = it["MIXI2_CLIENT_ID"]
                CLIENT_SECRET = it["MIXI2_CLIENT_SECRET"]
                TOKEN_ENDPOINT = it["MIXI2_TOKEN_ENDPOINT"]
                AUTH_KEY = it["MIXI2_AUTH_KEY"]
                ACCESS_TOKEN = it["MIXI2_ACCESS_TOKEN"]
                STREAM_HOST = it["MIXI2_STREAM_HOST"]
                TEST_ROOM_ID = it["MIXI2_TEST_ROOM_ID"]
            }
        }

        if (HOST == null) {
            throw IllegalStateException("No credentials exist for testing...")
        }
    }

    private suspend fun ensureToken() {
        if (ACCESS_TOKEN.isNullOrEmpty() && !CLIENT_ID.isNullOrEmpty()) {
            val request = AuthObtainTokenRequest().also {
                it.clientId = CLIENT_ID
                it.clientSecret = CLIENT_SECRET
                it.tokenEndpoint = TOKEN_ENDPOINT
            }
            val response = Mixi2Factory.instance(HOST!!, "").auth()
                .obtainToken(request)
            ACCESS_TOKEN = response.data.accessToken
        }
    }

    private fun readTestProps(): Map<String, Map<String, String>>? {
        val secretsFile = getEnvVar("SECRETS_FILE")
        val paths = listOfNotNull(
            secretsFile,
            "../secrets.json",
            "secrets.json",
        )
        for (path in paths) {
            try {
                val jsonStr = readFileText(path) ?: continue
                return json.decodeFromString<Map<String, Map<String, String>>>(jsonStr)
            } catch (_: Exception) {
            }
        }
        return null
    }
}
