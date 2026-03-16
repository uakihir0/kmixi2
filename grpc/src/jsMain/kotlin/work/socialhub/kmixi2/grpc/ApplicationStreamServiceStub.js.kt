package work.socialhub.kmixi2.grpc

import kotlinx.coroutines.flow.Flow
import social.mixi.application.service.application_stream.v1.SubscribeEventsRequest
import social.mixi.application.service.application_stream.v1.SubscribeEventsResponse
import work.socialhub.kgrpc.Channel
import work.socialhub.kgrpc.metadata.Metadata
import work.socialhub.kgrpc.rpc.serverStreamingRpc

private const val SERVICE = "/social.mixi.application.service.application_stream.v1.ApplicationService"

actual class ApplicationStreamServiceStub actual constructor(
    private val channel: Channel
) {
    actual fun subscribeEvents(
        request: WireMessageAdapter<SubscribeEventsRequest>,
        metadata: Metadata
    ): Flow<WireMessageAdapter<SubscribeEventsResponse>> {
        val companion = WireCompanionAdapter(
            "$SERVICE/SubscribeEvents",
            SubscribeEventsResponse.ADAPTER
        )
        return serverStreamingRpc(
            channel = channel,
            path = "$SERVICE/SubscribeEvents",
            request = request,
            responseDeserializer = companion,
            headers = metadata
        )
    }
}
