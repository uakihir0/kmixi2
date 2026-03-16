package work.socialhub.kmixi2.grpc

import io.grpc.CallOptions
import io.grpc.MethodDescriptor as JvmMethodDescriptor
import kotlinx.coroutines.flow.Flow
import social.mixi.application.service.application_stream.v1.SubscribeEventsRequest
import social.mixi.application.service.application_stream.v1.SubscribeEventsResponse
import work.socialhub.kgrpc.Channel
import work.socialhub.kgrpc.metadata.Metadata
import work.socialhub.kgrpc.rpc.serverStreamingRpc
import java.io.ByteArrayInputStream
import java.io.InputStream

private const val SERVICE = "social.mixi.application.service.application_stream.v1.ApplicationService"

actual class ApplicationStreamServiceStub actual constructor(
    private val channel: Channel
) {
    private fun <T : com.squareup.wire.Message<T, *>> wireMarshaller(
        adapter: com.squareup.wire.ProtoAdapter<T>,
        fullName: String
    ): JvmMethodDescriptor.Marshaller<WireMessageAdapter<T>> {
        return object : JvmMethodDescriptor.Marshaller<WireMessageAdapter<T>> {
            override fun stream(value: WireMessageAdapter<T>): InputStream =
                ByteArrayInputStream(value.serialize())
            override fun parse(stream: InputStream): WireMessageAdapter<T> =
                WireMessageAdapter(adapter.decode(stream.readBytes()), fullName)
        }
    }

    actual fun subscribeEvents(
        request: WireMessageAdapter<SubscribeEventsRequest>,
        metadata: Metadata
    ): Flow<WireMessageAdapter<SubscribeEventsResponse>> {
        val method = JvmMethodDescriptor.newBuilder<WireMessageAdapter<SubscribeEventsRequest>, WireMessageAdapter<SubscribeEventsResponse>>()
            .setType(JvmMethodDescriptor.MethodType.SERVER_STREAMING)
            .setFullMethodName("$SERVICE/SubscribeEvents")
            .setRequestMarshaller(wireMarshaller(SubscribeEventsRequest.ADAPTER, "$SERVICE.SubscribeEventsRequest"))
            .setResponseMarshaller(wireMarshaller(SubscribeEventsResponse.ADAPTER, "$SERVICE.SubscribeEventsResponse"))
            .build()

        return serverStreamingRpc(
            channel = channel,
            method = method,
            request = request,
            callOptions = CallOptions.DEFAULT,
            headers = metadata
        )
    }
}
