package work.socialhub.kmixi2.api.request.posts

import kotlin.js.JsExport

@JsExport
class PostsCreatePostRequest {
    var text: String? = null
    var inReplyToPostId: String? = null
    var quotedPostId: String? = null
    var mediaIdList: Array<String>? = null
    var maskType: String? = null
    var maskCaption: String? = null
    var publishingType: String? = null
}
