package work.socialhub.kmixi2.api.response.stamps

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import work.socialhub.kmixi2.entity.Post
import kotlin.js.JsExport

@JsExport
@Serializable
class StampsAddStampToPostResponse {
    @SerialName("post")
    var post: Post? = null
}
