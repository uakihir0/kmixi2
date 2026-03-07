package work.socialhub.kmixi2.stream

import work.socialhub.kmixi2.stream.listener.EventStreamListener
import work.socialhub.kmixi2.stream.listener.LifeCycleListener
import kotlin.js.JsExport

@JsExport
interface EventStream {
    suspend fun open()
    suspend fun close()
    fun isOpen(): Boolean
    fun onEvent(listener: EventStreamListener): EventStream
    fun onLifeCycle(listener: LifeCycleListener): EventStream
}
