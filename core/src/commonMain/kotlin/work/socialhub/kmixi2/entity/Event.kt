package work.socialhub.kmixi2.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
@Serializable
class Event {
    @SerialName("event_id")
    var eventId: String = ""

    @SerialName("event_type")
    var eventType: String? = null

    @SerialName("ping_event")
    var pingEvent: PingEvent? = null

    @SerialName("post_created_event")
    var postCreatedEvent: PostCreatedEvent? = null

    @SerialName("chat_message_received_event")
    var chatMessageReceivedEvent: ChatMessageReceivedEvent? = null
}

@JsExport
@Serializable
class PingEvent

@JsExport
@Serializable
class PostCreatedEvent {
    @SerialName("event_reason_list")
    var eventReasonList: Array<String> = arrayOf()

    @SerialName("post")
    var post: Post? = null

    @SerialName("issuer")
    var issuer: User? = null
}

@JsExport
@Serializable
class ChatMessageReceivedEvent {
    @SerialName("event_reason_list")
    var eventReasonList: Array<String> = arrayOf()

    @SerialName("message")
    var message: ChatMessage? = null

    @SerialName("issuer")
    var issuer: User? = null
}
