package work.socialhub.kmixi2.entity.constant

import kotlin.js.JsExport

@JsExport
enum class PostPublishingType(val value: String) {
    UNSPECIFIED("POST_PUBLISHING_TYPE_UNSPECIFIED"),
    NOT_PUBLISHING("POST_PUBLISHING_TYPE_NOT_PUBLISHING"),
    ;

    companion object {
        fun from(value: String): PostPublishingType =
            entries.find { it.value == value } ?: UNSPECIFIED
    }
}
