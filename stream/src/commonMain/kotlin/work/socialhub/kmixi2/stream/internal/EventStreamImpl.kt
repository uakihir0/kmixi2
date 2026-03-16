package work.socialhub.kmixi2.stream.internal

import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import social.mixi.application.service.application_stream.v1.SubscribeEventsRequest
import work.socialhub.kgrpc.Channel
import work.socialhub.kgrpc.metadata.Entry
import work.socialhub.kgrpc.metadata.Key
import work.socialhub.kgrpc.metadata.Metadata
import work.socialhub.kmixi2.grpc.ApplicationStreamServiceStub
import work.socialhub.kmixi2.grpc.WireMessageAdapter
import work.socialhub.kmixi2.internal.toEntity
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

    private var channel: Channel? = null
    private var job: Job? = null

    private fun createStub(): ApplicationStreamServiceStub {
        val parts = host.split(":")
        val hostname = parts[0]
        val port = if (parts.size > 1) parts[1].toInt() else 443
        val ch = Channel.Builder.forAddress(hostname, port).build()
        channel = ch
        return ApplicationStreamServiceStub(ch)
    }

    private fun authMetadata(): Metadata {
        val entries = mutableListOf<Entry<*>>()
        if (accessToken.isNotEmpty()) {
            entries += Entry.Ascii(
                Key.AsciiKey("authorization"),
                setOf("Bearer $accessToken")
            )
        }
        authKey?.let {
            entries += Entry.Ascii(
                Key.AsciiKey("x-auth-key"),
                setOf(it)
            )
        }
        return Metadata.of(entries)
    }

    override suspend fun open() {
        val stub = createStub()
        val request = WireMessageAdapter(SubscribeEventsRequest(), "SubscribeEventsRequest")
        val flow = stub.subscribeEvents(request, authMetadata())

        coroutineScope {
            job = launch {
                try {
                    isOpen = true
                    lifeCycleListener?.onConnect()
                    flow.collect { response ->
                        response.wire.events.forEach { event ->
                            val entity = event.toEntity()
                            when {
                                entity.pingEvent != null ->
                                    eventListener?.onPing()
                                entity.postCreatedEvent != null ->
                                    eventListener?.onPostCreated(entity.postCreatedEvent!!)
                                entity.chatMessageReceivedEvent != null ->
                                    eventListener?.onChatMessageReceived(entity.chatMessageReceivedEvent!!)
                            }
                        }
                    }
                } catch (e: Exception) {
                    lifeCycleListener?.onError(e)
                } finally {
                    isOpen = false
                    lifeCycleListener?.onDisconnect()
                }
            }
        }
    }

    override suspend fun close() {
        job?.cancelAndJoin()
        channel?.shutdown()
        channel = null
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
