package work.socialhub.kmixi2.entity.constant

import kotlin.js.JsExport

@JsExport
enum class PostMaskType(val value: String) {
    UNSPECIFIED("POST_MASK_TYPE_UNSPECIFIED"),
    SENSITIVE("POST_MASK_TYPE_SENSITIVE"),
    SPOILER("POST_MASK_TYPE_SPOILER"),
    ;

    companion object {
        fun from(value: String): PostMaskType =
            entries.find { it.value == value } ?: UNSPECIFIED
    }
}
