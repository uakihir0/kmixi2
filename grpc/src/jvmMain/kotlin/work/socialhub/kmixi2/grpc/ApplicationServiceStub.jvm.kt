package work.socialhub.kmixi2.grpc

import com.squareup.wire.ProtoAdapter
import io.grpc.CallOptions
import io.grpc.MethodDescriptor as JvmMethodDescriptor
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
import java.io.ByteArrayInputStream
import java.io.InputStream

private const val SERVICE = "social.mixi.application.service.application_api.v1.ApplicationService"

actual class ApplicationServiceStub actual constructor(
    private val channel: Channel
) {
    private fun <T : com.squareup.wire.Message<T, *>> wireMarshaller(
        adapter: ProtoAdapter<T>,
        fullName: String
    ): JvmMethodDescriptor.Marshaller<WireMessageAdapter<T>> {
        return object : JvmMethodDescriptor.Marshaller<WireMessageAdapter<T>> {
            override fun stream(value: WireMessageAdapter<T>): InputStream =
                ByteArrayInputStream(value.serialize())
            override fun parse(stream: InputStream): WireMessageAdapter<T> =
                WireMessageAdapter(adapter.decode(stream.readBytes()), fullName)
        }
    }

    private fun <REQ : com.squareup.wire.Message<REQ, *>, RESP : com.squareup.wire.Message<RESP, *>> method(
        name: String,
        reqAdapter: ProtoAdapter<REQ>,
        respAdapter: ProtoAdapter<RESP>,
    ): JvmMethodDescriptor<WireMessageAdapter<REQ>, WireMessageAdapter<RESP>> {
        return JvmMethodDescriptor.newBuilder<WireMessageAdapter<REQ>, WireMessageAdapter<RESP>>()
            .setType(JvmMethodDescriptor.MethodType.UNARY)
            .setFullMethodName("$SERVICE/$name")
            .setRequestMarshaller(wireMarshaller(reqAdapter, "$SERVICE.$name"))
            .setResponseMarshaller(wireMarshaller(respAdapter, "$SERVICE.$name"))
            .build()
    }

    actual suspend fun getUsers(
        request: WireMessageAdapter<GetUsersRequest>,
        metadata: Metadata
    ): WireMessageAdapter<GetUsersResponse> = unaryRpc(
        channel,
        method("GetUsers", GetUsersRequest.ADAPTER, GetUsersResponse.ADAPTER),
        request, CallOptions.DEFAULT, metadata
    )

    actual suspend fun getPosts(
        request: WireMessageAdapter<GetPostsRequest>,
        metadata: Metadata
    ): WireMessageAdapter<GetPostsResponse> = unaryRpc(
        channel,
        method("GetPosts", GetPostsRequest.ADAPTER, GetPostsResponse.ADAPTER),
        request, CallOptions.DEFAULT, metadata
    )

    actual suspend fun createPost(
        request: WireMessageAdapter<CreatePostRequest>,
        metadata: Metadata
    ): WireMessageAdapter<CreatePostResponse> = unaryRpc(
        channel,
        method("CreatePost", CreatePostRequest.ADAPTER, CreatePostResponse.ADAPTER),
        request, CallOptions.DEFAULT, metadata
    )

    actual suspend fun initiatePostMediaUpload(
        request: WireMessageAdapter<InitiatePostMediaUploadRequest>,
        metadata: Metadata
    ): WireMessageAdapter<InitiatePostMediaUploadResponse> = unaryRpc(
        channel,
        method("InitiatePostMediaUpload", InitiatePostMediaUploadRequest.ADAPTER, InitiatePostMediaUploadResponse.ADAPTER),
        request, CallOptions.DEFAULT, metadata
    )

    actual suspend fun getPostMediaStatus(
        request: WireMessageAdapter<GetPostMediaStatusRequest>,
        metadata: Metadata
    ): WireMessageAdapter<GetPostMediaStatusResponse> = unaryRpc(
        channel,
        method("GetPostMediaStatus", GetPostMediaStatusRequest.ADAPTER, GetPostMediaStatusResponse.ADAPTER),
        request, CallOptions.DEFAULT, metadata
    )

    actual suspend fun sendChatMessage(
        request: WireMessageAdapter<SendChatMessageRequest>,
        metadata: Metadata
    ): WireMessageAdapter<SendChatMessageResponse> = unaryRpc(
        channel,
        method("SendChatMessage", SendChatMessageRequest.ADAPTER, SendChatMessageResponse.ADAPTER),
        request, CallOptions.DEFAULT, metadata
    )

    actual suspend fun getStamps(
        request: WireMessageAdapter<GetStampsRequest>,
        metadata: Metadata
    ): WireMessageAdapter<GetStampsResponse> = unaryRpc(
        channel,
        method("GetStamps", GetStampsRequest.ADAPTER, GetStampsResponse.ADAPTER),
        request, CallOptions.DEFAULT, metadata
    )

    actual suspend fun addStampToPost(
        request: WireMessageAdapter<AddStampToPostRequest>,
        metadata: Metadata
    ): WireMessageAdapter<AddStampToPostResponse> = unaryRpc(
        channel,
        method("AddStampToPost", AddStampToPostRequest.ADAPTER, AddStampToPostResponse.ADAPTER),
        request, CallOptions.DEFAULT, metadata
    )
}
