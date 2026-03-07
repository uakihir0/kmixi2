package work.socialhub.kmixi2

import kotlinx.serialization.json.Json
import java.io.File
import kotlin.test.BeforeTest

open class AbstractTest {

    companion object {
        var HOST: String? = null
        var CLIENT_ID: String? = null
        var CLIENT_SECRET: String? = null
        var TOKEN_ENDPOINT: String? = null
        var AUTH_KEY: String? = null
        var ACCESS_TOKEN: String? = null
    }

    protected val json = Json {
        ignoreUnknownKeys = true
    }

    fun mixi2(): Mixi2 {
        return Mixi2Factory.instance(
            HOST!!,
            ACCESS_TOKEN ?: "",
            AUTH_KEY,
        )
    }

    @BeforeTest
    fun setupTest() {
        try {
            HOST = System.getenv("MIXI2_HOST")
                ?: System.getProperty("MIXI2_HOST")
            CLIENT_ID = System.getenv("MIXI2_CLIENT_ID")
                ?: System.getProperty("MIXI2_CLIENT_ID")
            CLIENT_SECRET = System.getenv("MIXI2_CLIENT_SECRET")
                ?: System.getProperty("MIXI2_CLIENT_SECRET")
            TOKEN_ENDPOINT = System.getenv("MIXI2_TOKEN_ENDPOINT")
                ?: System.getProperty("MIXI2_TOKEN_ENDPOINT")
            AUTH_KEY = System.getenv("MIXI2_AUTH_KEY")
                ?: System.getProperty("MIXI2_AUTH_KEY")
            ACCESS_TOKEN = System.getenv("MIXI2_ACCESS_TOKEN")
                ?: System.getProperty("MIXI2_ACCESS_TOKEN")
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
            }
        }

        if (HOST == null) {
            throw IllegalStateException("No credentials exist for testing...")
        }
    }

    private fun readTestProps(): Map<String, Map<String, String>>? {
        return try {
            val jsonStr = File("../secrets.json").readText()
            json.decodeFromString<Map<String, Map<String, String>>>(jsonStr)
        } catch (e: Exception) {
            null
        }
    }
}
