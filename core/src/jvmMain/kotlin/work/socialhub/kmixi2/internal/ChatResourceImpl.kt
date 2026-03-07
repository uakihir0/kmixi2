package work.socialhub.kmixi2.internal

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
    ): Response<ChatSendChatMessageResponse> {
        // TODO: Call ApplicationService.SendChatMessage via gRPC stub
        TODO("gRPC implementation pending")
    }

    override fun sendChatMessageBlocking(
        request: ChatSendChatMessageRequest
    ): Response<ChatSendChatMessageResponse> = toBlocking { sendChatMessage(request) }
}
