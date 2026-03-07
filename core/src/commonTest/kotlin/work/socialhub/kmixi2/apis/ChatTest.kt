package work.socialhub.kmixi2.apis

import kotlinx.coroutines.test.runTest
import work.socialhub.kmixi2.AbstractTest
import work.socialhub.kmixi2.api.request.chat.ChatSendChatMessageRequest
import kotlin.test.Test

class ChatTest : AbstractTest() {

    @Test
    fun testSendChatMessage() = runTest {
        val request = ChatSendChatMessageRequest().also {
            it.roomId = TEST_ROOM_ID
            it.text = "Hello from kmixi2"
        }
        val response = mixi2().chat().sendChatMessage(request)
        println(response.data.message?.messageId)
    }
}
