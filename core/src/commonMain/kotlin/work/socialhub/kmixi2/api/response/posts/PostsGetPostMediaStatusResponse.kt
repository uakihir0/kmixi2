package work.socialhub.kmixi2.api.response.posts

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
@Serializable
class PostsGetPostMediaStatusResponse {
    @SerialName("status")
    var status: String = ""
}
