package work.socialhub.kmixi2.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
@Serializable
class Post {
    @SerialName("post_id")
    var postId: String = ""

    @SerialName("is_deleted")
    var isDeleted: Boolean = false

    @SerialName("creator_id")
    var creatorId: String = ""

    @SerialName("text")
    var text: String = ""

    @SerialName("created_at")
    var createdAt: String? = null

    @SerialName("post_media_list")
    var postMediaList: Array<PostMedia> = arrayOf()

    @SerialName("in_reply_to_post_id")
    var inReplyToPostId: String? = null

    @SerialName("post_mask")
    var postMask: PostMask? = null

    @SerialName("visibility")
    var visibility: String? = null

    @SerialName("access_level")
    var accessLevel: String? = null

    @SerialName("stamps")
    var stamps: Array<PostStamp> = arrayOf()

    @SerialName("reader_stamp_id")
    var readerStampId: String? = null
}
