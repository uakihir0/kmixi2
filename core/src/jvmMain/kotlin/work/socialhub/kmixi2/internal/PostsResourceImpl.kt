package work.socialhub.kmixi2.internal

import social.mixi.application.constant.v1.PostMaskTypeOuterClass.PostMaskType
import social.mixi.application.constant.v1.PostPublishingTypeOuterClass.PostPublishingType
import social.mixi.application.model.v1.PostOuterClass
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
        val grpcRequest = Service.GetPostsRequest.newBuilder()
            .addAllPostIdList(request.postIdList?.toList() ?: emptyList())
            .build()
        val grpcResponse = stub.getPosts(grpcRequest)
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
        val builder = Service.CreatePostRequest.newBuilder()
            .setText(request.text ?: "")
        request.inReplyToPostId?.let { builder.setInReplyToPostId(it) }
        request.quotedPostId?.let { builder.setQuotedPostId(it) }
        request.mediaIdList?.let { builder.addAllMediaIdList(it.toList()) }
        request.maskType?.let { maskType ->
            val maskBuilder = PostOuterClass.PostMask.newBuilder()
                .setMaskType(PostMaskType.valueOf(maskType))
            request.maskCaption?.let { maskBuilder.setCaption(it) }
            builder.setPostMask(maskBuilder.build())
        }
        request.publishingType?.let {
            builder.setPublishingType(PostPublishingType.valueOf(it))
        }
        val grpcResponse = stub.createPost(builder.build())
        Response(PostsCreatePostResponse().also {
            it.post = if (grpcResponse.hasPost()) grpcResponse.post.toEntity() else null
        })
    }

    override fun createPostBlocking(
        request: PostsCreatePostRequest
    ): Response<PostsCreatePostResponse> = toBlocking { createPost(request) }

    override suspend fun initiatePostMediaUpload(
        request: PostsInitiatePostMediaUploadRequest
    ): Response<PostsInitiatePostMediaUploadResponse> = proceed {
        val builder = Service.InitiatePostMediaUploadRequest.newBuilder()
        request.contentType?.let { builder.setContentType(it) }
        request.dataSize?.let { builder.setDataSize(it) }
        request.mediaType?.let {
            builder.setMediaType(Service.InitiatePostMediaUploadRequest.Type.valueOf(it))
        }
        request.description?.let { builder.setDescription(it) }
        val grpcResponse = stub.initiatePostMediaUpload(builder.build())
        Response(PostsInitiatePostMediaUploadResponse().also {
            it.mediaId = grpcResponse.mediaId
            it.uploadUrl = grpcResponse.uploadUrl
        })
    }

    override fun initiatePostMediaUploadBlocking(
        request: PostsInitiatePostMediaUploadRequest
    ): Response<PostsInitiatePostMediaUploadResponse> = toBlocking { initiatePostMediaUpload(request) }

    override suspend fun getPostMediaStatus(
        request: PostsGetPostMediaStatusRequest
    ): Response<PostsGetPostMediaStatusResponse> = proceed {
        val grpcRequest = Service.GetPostMediaStatusRequest.newBuilder()
            .setMediaId(request.mediaId ?: "")
            .build()
        val grpcResponse = stub.getPostMediaStatus(grpcRequest)
        Response(PostsGetPostMediaStatusResponse().also {
            it.status = grpcResponse.status.name
        })
    }

    override fun getPostMediaStatusBlocking(
        request: PostsGetPostMediaStatusRequest
    ): Response<PostsGetPostMediaStatusResponse> = toBlocking { getPostMediaStatus(request) }
}
