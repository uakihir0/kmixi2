package work.socialhub.kmixi2.apis

import kotlinx.coroutines.test.runTest
import work.socialhub.kmixi2.AbstractTest
import work.socialhub.kmixi2.api.request.posts.PostsCreatePostRequest
import work.socialhub.kmixi2.api.request.posts.PostsGetPostsRequest
import kotlin.test.Test

class PostsTest : AbstractTest() {

    @Test
    fun testGetPosts() = runTest {
        val request = PostsGetPostsRequest().also {
            it.postIdList = arrayOf("5d68c21f-056c-4e40-8a92-9f249e8b232a")
        }
        val response = mixi2().posts().getPosts(request)
        println(response.data.posts.size)
    }

    @Test
    fun testCreatePost() = runTest {
        val request = PostsCreatePostRequest().also {
            it.text = "hello from kmixi2!!"
        }
        val response = mixi2().posts().createPost(request)
        println("postId: ${response.data.post?.postId}")
        println("text: ${response.data.post?.text}")
    }
}
