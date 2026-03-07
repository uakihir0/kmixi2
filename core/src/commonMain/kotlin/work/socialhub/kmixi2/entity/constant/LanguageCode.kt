package work.socialhub.kmixi2.entity.constant

import kotlin.js.JsExport

@JsExport
enum class LanguageCode(val value: String) {
    UNSPECIFIED("LANGUAGE_CODE_UNSPECIFIED"),
    JP("LANGUAGE_CODE_JP"),
    EN("LANGUAGE_CODE_EN"),
    ;

    companion object {
        fun from(value: String): LanguageCode =
            entries.find { it.value == value } ?: UNSPECIFIED
    }
}
