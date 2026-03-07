package work.socialhub.kmixi2.entity.constant

import kotlin.js.JsExport

@JsExport
enum class EventType(val value: String) {
    UNSPECIFIED("EVENT_TYPE_UNSPECIFIED"),
    PING("EVENT_TYPE_PING"),
    POST_CREATED("EVENT_TYPE_POST_CREATED"),
    CHAT_MESSAGE_RECEIVED("EVENT_TYPE_CHAT_MESSAGE_RECEIVED"),
    ;

    companion object {
        fun from(value: String): EventType =
            entries.find { it.value == value } ?: UNSPECIFIED
    }
}
