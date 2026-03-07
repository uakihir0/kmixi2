package work.socialhub.kmixi2.stream.internal

import work.socialhub.kmixi2.stream.EventStream
import work.socialhub.kmixi2.stream.StreamResource

class StreamResourceImpl(
    private val host: String,
    private val accessToken: String,
    private val authKey: String?,
) : StreamResource {

    override fun eventStream(): EventStream {
        // TODO: Create gRPC server-streaming connection
        //  to ApplicationStreamService.SubscribeEvents
        return EventStreamImpl(host, accessToken, authKey)
    }
}
