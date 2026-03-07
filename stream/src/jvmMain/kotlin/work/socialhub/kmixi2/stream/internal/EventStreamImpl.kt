package work.socialhub.kmixi2.stream.internal

import work.socialhub.kmixi2.stream.EventStream
import work.socialhub.kmixi2.stream.listener.EventStreamListener
import work.socialhub.kmixi2.stream.listener.LifeCycleListener

class EventStreamImpl(
    private val host: String,
    private val accessToken: String,
    private val authKey: String?,
) : EventStream {

    private var eventListener: EventStreamListener? = null
    private var lifeCycleListener: LifeCycleListener? = null
    private var isOpen = false

    override suspend fun open() {
        // TODO: Open gRPC server-streaming connection
        //  to ApplicationStreamService.SubscribeEvents
        //  Parse incoming Event messages and dispatch to listeners
        TODO("gRPC streaming implementation pending")
    }

    override fun close() {
        // TODO: Close gRPC channel
        isOpen = false
    }

    override fun isOpen(): Boolean = isOpen

    override fun onEvent(listener: EventStreamListener): EventStream {
        eventListener = listener
        return this
    }

    override fun onLifeCycle(listener: LifeCycleListener): EventStream {
        lifeCycleListener = listener
        return this
    }
}
