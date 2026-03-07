package work.socialhub.kmixi2.api

import work.socialhub.kmixi2.api.request.posts.PostsCreatePostRequest
import work.socialhub.kmixi2.api.request.posts.PostsGetPostMediaStatusRequest
import work.socialhub.kmixi2.api.request.posts.PostsGetPostsRequest
import work.socialhub.kmixi2.api.request.posts.PostsInitiatePostMediaUploadRequest
import work.socialhub.kmixi2.api.response.Response
import work.socialhub.kmixi2.api.response.posts.PostsCreatePostResponse
import work.socialhub.kmixi2.api.response.posts.PostsGetPostMediaStatusResponse
import work.socialhub.kmixi2.api.response.posts.PostsGetPostsResponse
import work.socialhub.kmixi2.api.response.posts.PostsInitiatePostMediaUploadResponse
import kotlin.js.JsExport

@JsExport
interface PostsResource {

    suspend fun getPosts(
        request: PostsGetPostsRequest
    ): Response<PostsGetPostsResponse>

    @JsExport.Ignore
    fun getPostsBlocking(
        request: PostsGetPostsRequest
    ): Response<PostsGetPostsResponse>

    suspend fun createPost(
        request: PostsCreatePostRequest
    ): Response<PostsCreatePostResponse>

    @JsExport.Ignore
    fun createPostBlocking(
        request: PostsCreatePostRequest
    ): Response<PostsCreatePostResponse>

    suspend fun initiatePostMediaUpload(
        request: PostsInitiatePostMediaUploadRequest
    ): Response<PostsInitiatePostMediaUploadResponse>

    @JsExport.Ignore
    fun initiatePostMediaUploadBlocking(
        request: PostsInitiatePostMediaUploadRequest
    ): Response<PostsInitiatePostMediaUploadResponse>

    suspend fun getPostMediaStatus(
        request: PostsGetPostMediaStatusRequest
    ): Response<PostsGetPostMediaStatusResponse>

    @JsExport.Ignore
    fun getPostMediaStatusBlocking(
        request: PostsGetPostMediaStatusRequest
    ): Response<PostsGetPostMediaStatusResponse>
}
