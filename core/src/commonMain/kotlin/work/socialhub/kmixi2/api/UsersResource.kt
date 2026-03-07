package work.socialhub.kmixi2.api

import work.socialhub.kmixi2.api.request.users.UsersGetUsersRequest
import work.socialhub.kmixi2.api.response.Response
import work.socialhub.kmixi2.api.response.users.UsersGetUsersResponse
import kotlin.js.JsExport

@JsExport
interface UsersResource {

    suspend fun getUsers(
        request: UsersGetUsersRequest
    ): Response<UsersGetUsersResponse>

    @JsExport.Ignore
    fun getUsersBlocking(
        request: UsersGetUsersRequest
    ): Response<UsersGetUsersResponse>
}
