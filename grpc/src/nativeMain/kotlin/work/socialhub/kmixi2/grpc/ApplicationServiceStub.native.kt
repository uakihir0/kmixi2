package work.socialhub.kmixi2.grpc

import com.squareup.wire.ProtoAdapter
import social.mixi.application.service.application_api.v1.AddStampToPostRequest
import social.mixi.application.service.application_api.v1.AddStampToPostResponse
import social.mixi.application.service.application_api.v1.CreatePostRequest
import social.mixi.application.service.application_api.v1.CreatePostResponse
import social.mixi.application.service.application_api.v1.GetPostMediaStatusRequest
import social.mixi.application.service.application_api.v1.GetPostMediaStatusResponse
import social.mixi.application.service.application_api.v1.GetPostsRequest
import social.mixi.application.service.application_api.v1.GetPostsResponse
import social.mixi.application.service.application_api.v1.GetStampsRequest
import social.mixi.application.service.application_api.v1.GetStampsResponse
import social.mixi.application.service.application_api.v1.GetUsersRequest
import social.mixi.application.service.application_api.v1.GetUsersResponse
import social.mixi.application.service.application_api.v1.InitiatePostMediaUploadRequest
import social.mixi.application.service.application_api.v1.InitiatePostMediaUploadResponse
import social.mixi.application.service.application_api.v1.SendChatMessageRequest
import social.mixi.application.service.application_api.v1.SendChatMessageResponse
import work.socialhub.kgrpc.Channel
import work.socialhub.kgrpc.metadata.Metadata
import work.socialhub.kgrpc.rpc.unaryRpc

private const val SERVICE = "/social.mixi.application.service.application_api.v1.ApplicationService"

actual class ApplicationServiceStub actual constructor(
    private val channel: Channel
) {
    private suspend fun <REQ : com.squareup.wire.Message<REQ, *>, RESP : com.squareup.wire.Message<RESP, *>> call(
        method: String,
        request: WireMessageAdapter<REQ>,
        respAdapter: ProtoAdapter<RESP>,
        metadata: Metadata
    ): WireMessageAdapter<RESP> {
        val companion = WireCompanionAdapter("$SERVICE/$method", respAdapter)
        return unaryRpc(
            channel = channel,
            path = "$SERVICE/$method",
            request = request,
            responseDeserializer = companion,
            headers = metadata
        )
    }

    actual suspend fun getUsers(
        request: WireMessageAdapter<GetUsersRequest>,
        metadata: Metadata
    ): WireMessageAdapter<GetUsersResponse> =
        call("GetUsers", request, GetUsersResponse.ADAPTER, metadata)

    actual suspend fun getPosts(
        request: WireMessageAdapter<GetPostsRequest>,
        metadata: Metadata
    ): WireMessageAdapter<GetPostsResponse> =
        call("GetPosts", request, GetPostsResponse.ADAPTER, metadata)

    actual suspend fun createPost(
        request: WireMessageAdapter<CreatePostRequest>,
        metadata: Metadata
    ): WireMessageAdapter<CreatePostResponse> =
        call("CreatePost", request, CreatePostResponse.ADAPTER, metadata)

    actual suspend fun initiatePostMediaUpload(
        request: WireMessageAdapter<InitiatePostMediaUploadRequest>,
        metadata: Metadata
    ): WireMessageAdapter<InitiatePostMediaUploadResponse> =
        call("InitiatePostMediaUpload", request, InitiatePostMediaUploadResponse.ADAPTER, metadata)

    actual suspend fun getPostMediaStatus(
        request: WireMessageAdapter<GetPostMediaStatusRequest>,
        metadata: Metadata
    ): WireMessageAdapter<GetPostMediaStatusResponse> =
        call("GetPostMediaStatus", request, GetPostMediaStatusResponse.ADAPTER, metadata)

    actual suspend fun sendChatMessage(
        request: WireMessageAdapter<SendChatMessageRequest>,
        metadata: Metadata
    ): WireMessageAdapter<SendChatMessageResponse> =
        call("SendChatMessage", request, SendChatMessageResponse.ADAPTER, metadata)

    actual suspend fun getStamps(
        request: WireMessageAdapter<GetStampsRequest>,
        metadata: Metadata
    ): WireMessageAdapter<GetStampsResponse> =
        call("GetStamps", request, GetStampsResponse.ADAPTER, metadata)

    actual suspend fun addStampToPost(
        request: WireMessageAdapter<AddStampToPostRequest>,
        metadata: Metadata
    ): WireMessageAdapter<AddStampToPostResponse> =
        call("AddStampToPost", request, AddStampToPostResponse.ADAPTER, metadata)
}
