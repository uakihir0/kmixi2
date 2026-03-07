package work.socialhub.kmixi2.api.response.users

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import work.socialhub.kmixi2.entity.User
import kotlin.js.JsExport

@JsExport
@Serializable
class UsersGetUsersResponse {
    @SerialName("users")
    var users: Array<User> = arrayOf()
}
