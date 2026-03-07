package work.socialhub.kmixi2.entity.constant

import kotlin.js.JsExport

@JsExport
enum class PostAccessLevel(val value: String) {
    UNSPECIFIED("POST_ACCESS_LEVEL_UNSPECIFIED"),
    PUBLIC("POST_ACCESS_LEVEL_PUBLIC"),
    PRIVATE("POST_ACCESS_LEVEL_PRIVATE"),
    ;

    companion object {
        fun from(value: String): PostAccessLevel =
            entries.find { it.value == value } ?: UNSPECIFIED
    }
}
