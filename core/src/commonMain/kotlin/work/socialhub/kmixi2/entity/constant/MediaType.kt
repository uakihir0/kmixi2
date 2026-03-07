package work.socialhub.kmixi2.entity.constant

import kotlin.js.JsExport

@JsExport
enum class MediaType(val value: String) {
    UNSPECIFIED("MEDIA_TYPE_UNSPECIFIED"),
    IMAGE("MEDIA_TYPE_IMAGE"),
    VIDEO("MEDIA_TYPE_VIDEO"),
    ;

    companion object {
        fun from(value: String): MediaType =
            entries.find { it.value == value } ?: UNSPECIFIED
    }
}
