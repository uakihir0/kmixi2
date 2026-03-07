package work.socialhub.kmixi2.entity.constant

import kotlin.js.JsExport

@JsExport
enum class PostMediaType(val value: String) {
    UNSPECIFIED("POST_MEDIA_TYPE_UNSPECIFIED"),
    IMAGE("POST_MEDIA_TYPE_IMAGE"),
    VIDEO("POST_MEDIA_TYPE_VIDEO"),
    ;

    companion object {
        fun from(value: String): PostMediaType =
            entries.find { it.value == value } ?: UNSPECIFIED
    }
}
