package work.socialhub.kmixi2.entity.constant

import kotlin.js.JsExport

@JsExport
enum class UserAccessLevel(val value: String) {
    UNSPECIFIED("USER_ACCESS_LEVEL_UNSPECIFIED"),
    PUBLIC("USER_ACCESS_LEVEL_PUBLIC"),
    PRIVATE("USER_ACCESS_LEVEL_PRIVATE"),
    ;

    companion object {
        fun from(value: String): UserAccessLevel =
            entries.find { it.value == value } ?: UNSPECIFIED
    }
}
