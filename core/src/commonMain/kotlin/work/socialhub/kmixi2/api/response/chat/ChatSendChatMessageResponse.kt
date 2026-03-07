package work.socialhub.kmixi2.api.response.chat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import work.socialhub.kmixi2.entity.ChatMessage
import kotlin.js.JsExport

@JsExport
@Serializable
class ChatSendChatMessageResponse {
    @SerialName("message")
    var message: ChatMessage? = null
}
