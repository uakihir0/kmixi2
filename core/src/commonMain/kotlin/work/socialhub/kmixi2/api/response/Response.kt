package work.socialhub.kmixi2.api.response

import kotlin.js.JsExport

@JsExport
class Response<T>(var data: T) {
    var json: String? = null
}
