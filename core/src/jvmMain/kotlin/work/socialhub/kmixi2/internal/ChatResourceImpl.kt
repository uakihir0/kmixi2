package work.socialhub.kmixi2.internal

import social.mixi.application.service.application_api.v1.Service
import work.socialhub.kmixi2.api.ChatResource
import work.socialhub.kmixi2.api.request.chat.ChatSendChatMessageRequest
import work.socialhub.kmixi2.api.response.Response
import work.socialhub.kmixi2.api.response.chat.ChatSendChatMessageResponse
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
        val builder = Service.SendChatMessageRequest.newBuilder()
            .setRoomId(request.roomId ?: "")
        request.text?.let { builder.setText(it) }
        request.mediaId?.let { builder.setMediaId(it) }
        val grpcResponse = stub.sendChatMessage(builder.build())
        Response(ChatSendChatMessageResponse().also {
            it.message = if (grpcResponse.hasMessage()) grpcResponse.message.toEntity() else null
        })
    }

    override fun sendChatMessageBlocking(
        request: ChatSendChatMessageRequest
    ): Response<ChatSendChatMessageResponse> = toBlocking { sendChatMessage(request) }
}
