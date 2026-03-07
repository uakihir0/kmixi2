package work.socialhub.kmixi2.apis

import kotlinx.coroutines.test.runTest
import work.socialhub.kmixi2.AbstractTest
import work.socialhub.kmixi2.api.request.users.UsersGetUsersRequest
import kotlin.test.Test

class UsersTest : AbstractTest() {

    @Test
    fun testGetUsers() = runTest {
        val request = UsersGetUsersRequest().also {
            it.userIdList = arrayOf("6e7d3d67-7ce0-4b67-a559-06668e363ce4")
        }
        val response = mixi2().users().getUsers(request)
        println(response.data.users.size)
    }
}
