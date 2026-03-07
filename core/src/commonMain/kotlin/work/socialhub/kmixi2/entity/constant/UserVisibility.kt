package work.socialhub.kmixi2.entity.constant

import kotlin.js.JsExport

@JsExport
enum class UserVisibility(val value: String) {
    UNSPECIFIED("USER_VISIBILITY_UNSPECIFIED"),
    VISIBLE("USER_VISIBILITY_VISIBLE"),
    INVISIBLE("USER_VISIBILITY_INVISIBLE"),
    ;

    companion object {
        fun from(value: String): UserVisibility =
            entries.find { it.value == value } ?: UNSPECIFIED
    }
}
