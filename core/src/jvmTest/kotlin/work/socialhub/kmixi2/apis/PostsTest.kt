package work.socialhub.kmixi2.apis

import kotlinx.coroutines.test.runTest
import work.socialhub.kmixi2.AbstractTest
import work.socialhub.kmixi2.api.request.posts.PostsGetPostsRequest
import kotlin.test.Test

class PostsTest : AbstractTest() {

    @Test
    fun testGetPosts() = runTest {
        val request = PostsGetPostsRequest().also {
            it.postIdList = arrayOf("test-post-id")
        }
        val response = mixi2().posts().getPosts(request)
        println(response.data.posts.size)
    }
}
