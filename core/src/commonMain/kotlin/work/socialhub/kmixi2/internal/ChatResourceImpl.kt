package work.socialhub.kmixi2.internal

import social.mixi.application.service.application_api.v1.SendChatMessageRequest
import work.socialhub.kmixi2.api.ChatResource
import work.socialhub.kmixi2.api.request.chat.ChatSendChatMessageRequest
import work.socialhub.kmixi2.api.response.Response
import work.socialhub.kmixi2.api.response.chat.ChatSendChatMessageResponse
import work.socialhub.kmixi2.grpc.WireMessageAdapter
import work.socialhub.kmixi2.util.toBlocking

class ChatResourceImpl(
    host: String,
    accessToken: String,
    authKey: String?,
) : AbstractResourceImpl(host, accessToken, authKey),
    ChatResource {

    override suspend fun sendChatMessage(
        request: ChatSendChatMessageRequest
    ): Response<ChatSendChatMessageResponse> = proceed {
        val grpcRequest = WireMessageAdapter(
            SendChatMessageRequest(
                room_id = request.roomId ?: "",
                text = request.text,
                media_id = request.mediaId,
            ), "SendChatMessageRequest"
        )
        val grpcResponse = stub.sendChatMessage(grpcRequest, authMetadata())
        Response(ChatSendChatMessageResponse().also {
            it.message = grpcResponse.wire.message_?.toEntity()
        })
    }

    override fun sendChatMessageBlocking(
        request: ChatSendChatMessageRequest
    ): Response<ChatSendChatMessageResponse> = toBlocking { sendChatMessage(request) }
}
