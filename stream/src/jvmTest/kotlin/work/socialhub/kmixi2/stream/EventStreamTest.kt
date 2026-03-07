package work.socialhub.kmixi2.stream

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import work.socialhub.kmixi2.Mixi2Factory
import work.socialhub.kmixi2.api.request.auth.AuthObtainTokenRequest
import work.socialhub.kmixi2.stream.listener.EventStreamListener
import work.socialhub.kmixi2.stream.listener.LifeCycleListener
import work.socialhub.kmixi2.entity.ChatMessageReceivedEvent
import work.socialhub.kmixi2.entity.PostCreatedEvent
import java.io.File
import kotlin.test.BeforeTest
import kotlin.test.Test

class EventStreamTest {

    companion object {
        var HOST: String? = null
        var STREAM_HOST: String? = null
        var CLIENT_ID: String? = null
        var CLIENT_SECRET: String? = null
        var TOKEN_ENDPOINT: String? = null
        var AUTH_KEY: String? = null
        var ACCESS_TOKEN: String? = null
    }

    private val json = Json { ignoreUnknownKeys = true }

    @BeforeTest
    fun setup() {
        if (HOST == null) {
            readTestProps()?.get("mixi2")?.let {
                HOST = it["MIXI2_HOST"]
                STREAM_HOST = it["MIXI2_STREAM_HOST"]
                CLIENT_ID = it["MIXI2_CLIENT_ID"]
                CLIENT_SECRET = it["MIXI2_CLIENT_SECRET"]
                TOKEN_ENDPOINT = it["MIXI2_TOKEN_ENDPOINT"]
                AUTH_KEY = it["MIXI2_AUTH_KEY"]
            }
        }

        if (HOST == null) throw IllegalStateException("No credentials")

        if (ACCESS_TOKEN.isNullOrEmpty() && !CLIENT_ID.isNullOrEmpty()) {
            val request = AuthObtainTokenRequest().also {
                it.clientId = CLIENT_ID
                it.clientSecret = CLIENT_SECRET
                it.tokenEndpoint = TOKEN_ENDPOINT
            }
            val response = Mixi2Factory.instance(HOST!!, "").auth()
                .obtainTokenBlocking(request)
            ACCESS_TOKEN = response.data.accessToken
        }
    }

    @Test
    fun testSubscribeEvents() = runBlocking {
        val streamHost = STREAM_HOST ?: HOST!!
        val mixi2 = Mixi2Factory.instance(streamHost, ACCESS_TOKEN ?: "", AUTH_KEY)
        val stream = mixi2.stream().eventStream()

        stream.onEvent(object : EventStreamListener {
            override fun onPing() {
                println("[PING]")
            }
            override fun onPostCreated(event: PostCreatedEvent) {
                println("[POST_CREATED] postId=${event.post?.postId}, creatorId=${event.post?.creatorId}, text=${event.post?.text}")
            }
            override fun onChatMessageReceived(event: ChatMessageReceivedEvent) {
                println("[CHAT] roomId=${event.message?.roomId}, text=${event.message?.text}, creatorId=${event.message?.creatorId}")
            }
        })

        stream.onLifeCycle(object : LifeCycleListener {
            override fun onConnect() { println("[LIFECYCLE] Connected") }
            override fun onDisconnect() { println("[LIFECYCLE] Disconnected") }
            override fun onError(e: Exception) { println("[LIFECYCLE] Error: ${e.message}") }
        })

        val job = launch { stream.open() }
        println("Waiting 60 seconds for events...")
        delay(60_000)
        stream.close()
        job.cancel()
        println("Done.")
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
