package work.socialhub.kmixi2.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
@Serializable
class OfficialStampSet {
    @SerialName("stamp_set_id")
    var stampSetId: String = ""

    @SerialName("name")
    var name: String = ""

    @SerialName("sprite_url")
    var spriteUrl: String = ""

    @SerialName("stamps")
    var stamps: Array<OfficialStamp> = arrayOf()

    @SerialName("start_at")
    var startAt: String? = null

    @SerialName("end_at")
    var endAt: String? = null

    @SerialName("stamp_set_type")
    var stampSetType: String? = null
}

@JsExport
@Serializable
class OfficialStamp {
    @SerialName("stamp_id")
    var stampId: String = ""

    @SerialName("index")
    var index: Int = 0

    @SerialName("search_tags")
    var searchTags: Array<String> = arrayOf()

    @SerialName("url")
    var url: String = ""
}
