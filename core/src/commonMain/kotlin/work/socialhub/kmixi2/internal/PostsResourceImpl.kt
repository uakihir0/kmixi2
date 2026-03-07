package work.socialhub.kmixi2.internal

import social.mixi.application.constant.v1.PostMaskType
import social.mixi.application.constant.v1.PostPublishingType
import social.mixi.application.model.v1.Post
import social.mixi.application.service.application_api.v1.Service
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
        val grpcRequest = Service.GetPostsRequest(
            post_id_listList = request.postIdList?.toList() ?: emptyList()
        )
        val grpcResponse = stub.GetPosts(grpcRequest, authMetadata())
        Response(PostsGetPostsResponse().also {
            it.posts = grpcResponse.postsList.map { p -> p.toEntity() }.toTypedArray()
        })
    }

    override fun getPostsBlocking(
        request: PostsGetPostsRequest
    ): Response<PostsGetPostsResponse> = toBlocking { getPosts(request) }

    override suspend fun createPost(
        request: PostsCreatePostRequest
    ): Response<PostsCreatePostResponse> = proceed {
        val grpcRequest = Service.CreatePostRequest(
            text = request.text ?: "",
            in_reply_to_post_id = request.inReplyToPostId,
            quoted_post_id = request.quotedPostId,
            media_id_listList = request.mediaIdList?.toList() ?: emptyList(),
            post_mask = request.maskType?.let { maskType ->
                Post.PostMask(
                    mask_type = PostMaskType.PostMaskType.valueOf(maskType),
                    caption = request.maskCaption ?: "",
                )
            },
            publishing_type = request.publishingType?.let {
                PostPublishingType.PostPublishingType.valueOf(it)
            },
        )
        val grpcResponse = stub.CreatePost(grpcRequest, authMetadata())
        Response(PostsCreatePostResponse().also {
            it.post = if (grpcResponse.isPostSet) grpcResponse.post.toEntity() else null
        })
    }

    override fun createPostBlocking(
        request: PostsCreatePostRequest
    ): Response<PostsCreatePostResponse> = toBlocking { createPost(request) }

    override suspend fun initiatePostMediaUpload(
        request: PostsInitiatePostMediaUploadRequest
    ): Response<PostsInitiatePostMediaUploadResponse> = proceed {
        val grpcRequest = Service.InitiatePostMediaUploadRequest(
            content_type = request.contentType ?: "",
            data_size = request.dataSize?.toULong() ?: 0uL,
            media_type = request.mediaType?.let {
                Service.InitiatePostMediaUploadRequest.Type.valueOf(it)
            } ?: Service.InitiatePostMediaUploadRequest.Type.TYPE_UNSPECIFIED,
            description = request.description,
        )
        val grpcResponse = stub.InitiatePostMediaUpload(grpcRequest, authMetadata())
        Response(PostsInitiatePostMediaUploadResponse().also {
            it.mediaId = grpcResponse.media_id
            it.uploadUrl = grpcResponse.upload_url
        })
    }

    override fun initiatePostMediaUploadBlocking(
        request: PostsInitiatePostMediaUploadRequest
    ): Response<PostsInitiatePostMediaUploadResponse> = toBlocking { initiatePostMediaUpload(request) }

    override suspend fun getPostMediaStatus(
        request: PostsGetPostMediaStatusRequest
    ): Response<PostsGetPostMediaStatusResponse> = proceed {
        val grpcRequest = Service.GetPostMediaStatusRequest(
            media_id = request.mediaId ?: ""
        )
        val grpcResponse = stub.GetPostMediaStatus(grpcRequest, authMetadata())
        Response(PostsGetPostMediaStatusResponse().also {
            it.status = grpcResponse.status.name
        })
    }

    override fun getPostMediaStatusBlocking(
        request: PostsGetPostMediaStatusRequest
    ): Response<PostsGetPostMediaStatusResponse> = toBlocking { getPostMediaStatus(request) }
}
