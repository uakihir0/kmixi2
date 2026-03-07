package work.socialhub.kmixi2.api.response.posts

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
@Serializable
class PostsInitiatePostMediaUploadResponse {
    @SerialName("media_id")
    var mediaId: String = ""

    @SerialName("upload_url")
    var uploadUrl: String = ""
}
