package work.socialhub.kmixi2.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
@Serializable
class PostMask {
    @SerialName("mask_type")
    var maskType: String? = null

    @SerialName("caption")
    var caption: String = ""
}
