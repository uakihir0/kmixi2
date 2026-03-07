package work.socialhub.kmixi2.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
@Serializable
class ChatMessage {
    @SerialName("room_id")
    var roomId: String = ""

    @SerialName("message_id")
    var messageId: String = ""

    @SerialName("creator_id")
    var creatorId: String = ""

    @SerialName("text")
    var text: String = ""

    @SerialName("created_at")
    var createdAt: String? = null

    @SerialName("media_list")
    var mediaList: Array<Media> = arrayOf()

    @SerialName("post_id")
    var postId: String? = null
}
