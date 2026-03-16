package work.socialhub.kmixi2.grpc

import kotlinx.coroutines.flow.Flow
import social.mixi.application.service.application_stream.v1.SubscribeEventsRequest
import social.mixi.application.service.application_stream.v1.SubscribeEventsResponse
import work.socialhub.kgrpc.Channel
import work.socialhub.kgrpc.metadata.Metadata

expect class ApplicationStreamServiceStub(channel: Channel) {
    fun subscribeEvents(
        request: WireMessageAdapter<SubscribeEventsRequest>,
        metadata: Metadata
    ): Flow<WireMessageAdapter<SubscribeEventsResponse>>
}
