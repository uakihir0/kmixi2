package work.socialhub.kmixi2.internal

import social.mixi.application.constant.v1.PostMaskType
import social.mixi.application.constant.v1.PostPublishingType
import social.mixi.application.model.v1.PostMask
import social.mixi.application.service.application_api.v1.CreatePostRequest
import social.mixi.application.service.application_api.v1.GetPostMediaStatusRequest
import social.mixi.application.service.application_api.v1.GetPostsRequest
import social.mixi.application.service.application_api.v1.InitiatePostMediaUploadRequest
import work.socialhub.kmixi2.api.PostsResource
import work.socialhub.kmixi2.api.request.posts.PostsCreatePostRequest
import work.socialhub.kmixi2.api.request.posts.PostsGetPostMediaStatusRequest
import work.socialhub.kmixi2.api.request.posts.PostsGetPostsRequest
import work.socialhub.kmixi2.api.request.posts.PostsInitiatePostMediaUploadRequest
import work.socialhub.kmixi2.api.response.Response
import work.socialhub.kmixi2.api.response.posts.PostsCreatePostResponse
import work.socialhub.kmixi2.api.response.posts.PostsGetPostMediaStatusResponse
import work.socialhub.kmixi2.api.response.posts.PostsGetPostsResponse
import work.socialhub.kmixi2.api.response.posts.PostsInitiatePostMediaUploadResponse
import work.socialhub.kmixi2.grpc.WireMessageAdapter
import work.socialhub.kmixi2.util.toBlocking

class PostsResourceImpl(
    host: String,
    accessToken: String,
    authKey: String?,
) : AbstractResourceImpl(host, accessToken, authKey),
    PostsResource {

    override suspend fun getPosts(
        request: PostsGetPostsRequest
    ): Response<PostsGetPostsResponse> = proceed {
        val grpcRequest = WireMessageAdapter(
            GetPostsRequest(
                post_id_list = request.postIdList?.toList() ?: emptyList()
            ), "GetPostsRequest"
        )
        val grpcResponse = stub.getPosts(grpcRequest, authMetadata())
        Response(PostsGetPostsResponse().also {
            it.posts = grpcResponse.wire.posts.map { p -> p.toEntity() }.toTypedArray()
        })
    }

    override fun getPostsBlocking(
        request: PostsGetPostsRequest
    ): Response<PostsGetPostsResponse> = toBlocking { getPosts(request) }

    override suspend fun createPost(
        request: PostsCreatePostRequest
    ): Response<PostsCreatePostResponse> = proceed {
        val grpcRequest = WireMessageAdapter(
            CreatePostRequest(
                text = request.text ?: "",
                in_reply_to_post_id = request.inReplyToPostId,
                quoted_post_id = request.quotedPostId,
                media_id_list = request.mediaIdList?.toList() ?: emptyList(),
                post_mask = request.maskType?.let { maskType ->
                    PostMask(
                        mask_type = PostMaskType.valueOf(maskType),
                        caption = request.maskCaption ?: "",
                    )
                },
                publishing_type = request.publishingType?.let {
                    PostPublishingType.valueOf(it)
                },
            ), "CreatePostRequest"
        )
        val grpcResponse = stub.createPost(grpcRequest, authMetadata())
        Response(PostsCreatePostResponse().also {
            it.post = grpcResponse.wire.post?.toEntity()
        })
    }

    override fun createPostBlocking(
        request: PostsCreatePostRequest
    ): Response<PostsCreatePostResponse> = toBlocking { createPost(request) }

    override suspend fun initiatePostMediaUpload(
        request: PostsInitiatePostMediaUploadRequest
    ): Response<PostsInitiatePostMediaUploadResponse> = proceed {
        val grpcRequest = WireMessageAdapter(
            InitiatePostMediaUploadRequest(
                content_type = request.contentType ?: "",
                data_size = request.dataSize?.toLong() ?: 0L,
                media_type = request.mediaType?.let {
                    InitiatePostMediaUploadRequest.Type.valueOf(it)
                } ?: InitiatePostMediaUploadRequest.Type.TYPE_UNSPECIFIED,
                description = request.description,
            ), "InitiatePostMediaUploadRequest"
        )
        val grpcResponse = stub.initiatePostMediaUpload(grpcRequest, authMetadata())
        Response(PostsInitiatePostMediaUploadResponse().also {
            it.mediaId = grpcResponse.wire.media_id
            it.uploadUrl = grpcResponse.wire.upload_url
        })
    }

    override fun initiatePostMediaUploadBlocking(
        request: PostsInitiatePostMediaUploadRequest
    ): Response<PostsInitiatePostMediaUploadResponse> = toBlocking { initiatePostMediaUpload(request) }

    override suspend fun getPostMediaStatus(
        request: PostsGetPostMediaStatusRequest
    ): Response<PostsGetPostMediaStatusResponse> = proceed {
        val grpcRequest = WireMessageAdapter(
            GetPostMediaStatusRequest(
                media_id = request.mediaId ?: ""
            ), "GetPostMediaStatusRequest"
        )
        val grpcResponse = stub.getPostMediaStatus(grpcRequest, authMetadata())
        Response(PostsGetPostMediaStatusResponse().also {
            it.status = grpcResponse.wire.status?.name ?: ""
        })
    }

    override fun getPostMediaStatusBlocking(
        request: PostsGetPostMediaStatusRequest
    ): Response<PostsGetPostMediaStatusResponse> = toBlocking { getPostMediaStatus(request) }
}
