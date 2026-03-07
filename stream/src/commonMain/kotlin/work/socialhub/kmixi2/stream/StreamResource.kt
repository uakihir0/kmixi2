package work.socialhub.kmixi2.stream

import kotlin.js.JsExport

@JsExport
interface StreamResource {
    fun eventStream(): EventStream
}
