package work.socialhub.kmixi2.api.response.posts

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import work.socialhub.kmixi2.entity.Post
import kotlin.js.JsExport

@JsExport
@Serializable
class PostsGetPostsResponse {
    @SerialName("posts")
    var posts: Array<Post> = arrayOf()
}
