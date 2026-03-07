package work.socialhub.kmixi2.api.request.stamps

import kotlin.js.JsExport

@JsExport
class StampsAddStampToPostRequest {
    var postId: String? = null
    var stampId: String? = null
}
