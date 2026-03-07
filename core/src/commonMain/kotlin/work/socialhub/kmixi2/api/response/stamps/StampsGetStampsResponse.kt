package work.socialhub.kmixi2.api.response.stamps

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import work.socialhub.kmixi2.entity.OfficialStampSet
import kotlin.js.JsExport

@JsExport
@Serializable
class StampsGetStampsResponse {
    @SerialName("official_stamp_sets")
    var officialStampSets: Array<OfficialStampSet> = arrayOf()
}
