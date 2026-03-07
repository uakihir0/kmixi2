package work.socialhub.kmixi2.entity.constant

import kotlin.js.JsExport

@JsExport
enum class StampSetType(val value: String) {
    UNSPECIFIED("STAMP_SET_TYPE_UNSPECIFIED"),
    DEFAULT("STAMP_SET_TYPE_DEFAULT"),
    SEASONAL("STAMP_SET_TYPE_SEASONAL"),
    ;

    companion object {
        fun from(value: String): StampSetType =
            entries.find { it.value == value } ?: UNSPECIFIED
    }
}
