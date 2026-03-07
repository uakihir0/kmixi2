package work.socialhub.kmixi2.stream.internal

import io.grpc.CallOptions
import io.grpc.Channel
import io.grpc.ClientCall
import io.grpc.ClientInterceptor
import io.grpc.ForwardingClientCall.SimpleForwardingClientCall
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import io.grpc.Metadata
import io.grpc.MethodDescriptor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import social.mixi.application.service.application_stream.v1.ApplicationServiceGrpcKt
import social.mixi.application.service.application_stream.v1.Service
import work.socialhub.kmixi2.internal.toEntity
import work.socialhub.kmixi2.stream.EventStream
import work.socialhub.kmixi2.stream.listener.EventStreamListener
import work.socialhub.kmixi2.stream.listener.LifeCycleListener
import java.util.concurrent.TimeUnit

class EventStreamImpl(
    private val host: String,
    private val accessToken: String,
    private val authKey: String?,
) : EventStream {

    private var eventListener: EventStreamListener? = null
    private var lifeCycleListener: LifeCycleListener? = null
    private var isOpen = false

    private var channel: ManagedChannel? = null
    private var job: Job? = null

    private fun createStub(): ApplicationServiceGrpcKt.ApplicationServiceCoroutineStub {
        val ch = ManagedChannelBuilder.forTarget(host)
            .useTransportSecurity()
            .build()
        channel = ch

        val interceptor = object : ClientInterceptor {
            override fun <ReqT, RespT> interceptCall(
                method: MethodDescriptor<ReqT, RespT>,
                callOptions: CallOptions,
                next: Channel
            ): ClientCall<ReqT, RespT> {
                return object : SimpleForwardingClientCall<ReqT, RespT>(
                    next.newCall(method, callOptions)
                ) {
                    override fun start(responseListener: Listener<RespT>, headers: Metadata) {
                        if (accessToken.isNotEmpty()) {
                            headers.put(
                                Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER),
                                "Bearer $accessToken"
                            )
                        }
                        authKey?.let {
                            headers.put(
                                Metadata.Key.of("x-auth-key", Metadata.ASCII_STRING_MARSHALLER),
                                it
                            )
                        }
                        super.start(responseListener, headers)
                    }
                }
            }
        }

        return ApplicationServiceGrpcKt.ApplicationServiceCoroutineStub(ch)
            .withInterceptors(interceptor)
    }

    override suspend fun open() {
        val stub = createStub()
        val request = Service.SubscribeEventsRequest.newBuilder().build()
        val flow = stub.subscribeEvents(request)

        coroutineScope {
            job = launch(Dispatchers.IO) {
                try {
                    isOpen = true
                    lifeCycleListener?.onConnect()
                    flow.collect { response ->
                        response.eventsList.forEach { event ->
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

    override fun close() {
        kotlinx.coroutines.runBlocking {
            job?.cancelAndJoin()
        }
        channel?.shutdown()?.awaitTermination(5, TimeUnit.SECONDS)
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
