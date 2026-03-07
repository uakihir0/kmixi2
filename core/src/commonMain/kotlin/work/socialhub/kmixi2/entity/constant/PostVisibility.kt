package work.socialhub.kmixi2.entity.constant

import kotlin.js.JsExport

@JsExport
enum class PostVisibility(val value: String) {
    UNSPECIFIED("POST_VISIBILITY_UNSPECIFIED"),
    VISIBLE("POST_VISIBILITY_VISIBLE"),
    INVISIBLE("POST_VISIBILITY_INVISIBLE"),
    ;

    companion object {
        fun from(value: String): PostVisibility =
            entries.find { it.value == value } ?: UNSPECIFIED
    }
}
