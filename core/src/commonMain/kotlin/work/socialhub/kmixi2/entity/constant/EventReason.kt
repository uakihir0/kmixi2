package work.socialhub.kmixi2.entity.constant

import kotlin.js.JsExport

@JsExport
enum class EventReason(val value: String) {
    UNSPECIFIED("EVENT_REASON_UNSPECIFIED"),
    PING("EVENT_REASON_PING"),
    POST_REPLY("EVENT_REASON_POST_REPLY"),
    POST_MENTIONED("EVENT_REASON_POST_MENTIONED"),
    POST_QUOTED("EVENT_REASON_POST_QUOTED"),
    DIRECT_MESSAGE_RECEIVED("EVENT_REASON_DIRECT_MESSAGE_RECEIVED"),
    ;

    companion object {
        fun from(value: String): EventReason =
            entries.find { it.value == value } ?: UNSPECIFIED
    }
}
