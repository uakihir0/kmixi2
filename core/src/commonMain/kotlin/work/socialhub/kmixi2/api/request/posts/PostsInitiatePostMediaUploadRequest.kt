package work.socialhub.kmixi2.api.request.posts

import kotlin.js.JsExport

@JsExport
class PostsInitiatePostMediaUploadRequest {
    var contentType: String? = null
    var dataSize: Long? = null
    var mediaType: String? = null
    var description: String? = null
}
