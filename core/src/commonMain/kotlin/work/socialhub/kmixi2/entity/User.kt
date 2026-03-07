package work.socialhub.kmixi2.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
@Serializable
class User {
    @SerialName("user_id")
    var userId: String = ""

    @SerialName("is_disabled")
    var isDisabled: Boolean = false

    @SerialName("name")
    var name: String = ""

    @SerialName("display_name")
    var displayName: String = ""

    @SerialName("profile")
    var profile: String = ""

    @SerialName("user_avatar")
    var userAvatar: UserAvatar? = null

    @SerialName("visibility")
    var visibility: String? = null

    @SerialName("access_level")
    var accessLevel: String? = null
}
