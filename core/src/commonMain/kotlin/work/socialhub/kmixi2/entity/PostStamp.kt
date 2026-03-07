package work.socialhub.kmixi2.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
@Serializable
class PostStamp {
    @SerialName("stamp")
    var stamp: MediaStamp? = null

    @SerialName("count")
    var count: Long = 0
}
