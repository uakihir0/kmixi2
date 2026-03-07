package work.socialhub.kmixi2.internal

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
    ): Response<PostsGetPostsResponse> {
        // TODO: Call ApplicationService.GetPosts via gRPC stub
        TODO("gRPC implementation pending")
    }

    override fun getPostsBlocking(
        request: PostsGetPostsRequest
    ): Response<PostsGetPostsResponse> = toBlocking { getPosts(request) }

    override suspend fun createPost(
        request: PostsCreatePostRequest
    ): Response<PostsCreatePostResponse> {
        // TODO: Call ApplicationService.CreatePost via gRPC stub
        TODO("gRPC implementation pending")
    }

    override fun createPostBlocking(
        request: PostsCreatePostRequest
    ): Response<PostsCreatePostResponse> = toBlocking { createPost(request) }

    override suspend fun initiatePostMediaUpload(
        request: PostsInitiatePostMediaUploadRequest
    ): Response<PostsInitiatePostMediaUploadResponse> {
        // TODO: Call ApplicationService.InitiatePostMediaUpload via gRPC stub
        TODO("gRPC implementation pending")
    }

    override fun initiatePostMediaUploadBlocking(
        request: PostsInitiatePostMediaUploadRequest
    ): Response<PostsInitiatePostMediaUploadResponse> = toBlocking { initiatePostMediaUpload(request) }

    override suspend fun getPostMediaStatus(
        request: PostsGetPostMediaStatusRequest
    ): Response<PostsGetPostMediaStatusResponse> {
        // TODO: Call ApplicationService.GetPostMediaStatus via gRPC stub
        TODO("gRPC implementation pending")
    }

    override fun getPostMediaStatusBlocking(
        request: PostsGetPostMediaStatusRequest
    ): Response<PostsGetPostMediaStatusResponse> = toBlocking { getPostMediaStatus(request) }
}
