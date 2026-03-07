package work.socialhub.kmixi2.api

import work.socialhub.kmixi2.api.request.chat.ChatSendChatMessageRequest
import work.socialhub.kmixi2.api.response.Response
import work.socialhub.kmixi2.api.response.chat.ChatSendChatMessageResponse
import kotlin.js.JsExport

@JsExport
interface ChatResource {

    suspend fun sendChatMessage(
        request: ChatSendChatMessageRequest
    ): Response<ChatSendChatMessageResponse>

    @JsExport.Ignore
    fun sendChatMessageBlocking(
        request: ChatSendChatMessageRequest
    ): Response<ChatSendChatMessageResponse>
}
