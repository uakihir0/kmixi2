package work.socialhub.kmixi2.api.request.chat

import kotlin.js.JsExport

@JsExport
class ChatSendChatMessageRequest {
    var roomId: String? = null
    var text: String? = null
    var mediaId: String? = null
}
