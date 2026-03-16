package work.socialhub.kmixi2.grpc

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

expect class ApplicationServiceStub(channel: Channel) {
    suspend fun getUsers(
        request: WireMessageAdapter<GetUsersRequest>,
        metadata: Metadata
    ): WireMessageAdapter<GetUsersResponse>

    suspend fun getPosts(
        request: WireMessageAdapter<GetPostsRequest>,
        metadata: Metadata
    ): WireMessageAdapter<GetPostsResponse>

    suspend fun createPost(
        request: WireMessageAdapter<CreatePostRequest>,
        metadata: Metadata
    ): WireMessageAdapter<CreatePostResponse>

    suspend fun initiatePostMediaUpload(
        request: WireMessageAdapter<InitiatePostMediaUploadRequest>,
        metadata: Metadata
    ): WireMessageAdapter<InitiatePostMediaUploadResponse>

    suspend fun getPostMediaStatus(
        request: WireMessageAdapter<GetPostMediaStatusRequest>,
        metadata: Metadata
    ): WireMessageAdapter<GetPostMediaStatusResponse>

    suspend fun sendChatMessage(
        request: WireMessageAdapter<SendChatMessageRequest>,
        metadata: Metadata
    ): WireMessageAdapter<SendChatMessageResponse>

    suspend fun getStamps(
        request: WireMessageAdapter<GetStampsRequest>,
        metadata: Metadata
    ): WireMessageAdapter<GetStampsResponse>

    suspend fun addStampToPost(
        request: WireMessageAdapter<AddStampToPostRequest>,
        metadata: Metadata
    ): WireMessageAdapter<AddStampToPostResponse>
}
