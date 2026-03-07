package work.socialhub.kmixi2.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
@Serializable
class UserAvatar {
    @SerialName("large_image_url")
    var largeImageUrl: String = ""

    @SerialName("large_image_mime_type")
    var largeImageMimeType: String = ""

    @SerialName("large_image_height")
    var largeImageHeight: Int = 0

    @SerialName("large_image_width")
    var largeImageWidth: Int = 0

    @SerialName("small_image_url")
    var smallImageUrl: String = ""

    @SerialName("small_image_mime_type")
    var smallImageMimeType: String = ""

    @SerialName("small_image_height")
    var smallImageHeight: Int = 0

    @SerialName("small_image_width")
    var smallImageWidth: Int = 0
}
