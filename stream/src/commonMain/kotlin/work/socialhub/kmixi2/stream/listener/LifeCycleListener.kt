package work.socialhub.kmixi2.stream.listener

import kotlin.js.JsExport

@JsExport
interface LifeCycleListener {
    fun onConnect()
    fun onDisconnect()
    fun onError(e: Exception)
}
