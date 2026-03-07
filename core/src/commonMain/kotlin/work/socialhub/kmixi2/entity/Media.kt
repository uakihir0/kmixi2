package work.socialhub.kmixi2.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
@Serializable
class Media {
    @SerialName("media_type")
    var mediaType: String? = null

    @SerialName("image")
    var image: MediaImage? = null

    @SerialName("video")
    var video: MediaVideo? = null
}

@JsExport
@Serializable
class MediaImage {
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

@JsExport
@Serializable
class MediaVideo {
    @SerialName("video_url")
    var videoUrl: String = ""

    @SerialName("video_mime_type")
    var videoMimeType: String = ""

    @SerialName("video_height")
    var videoHeight: Int = 0

    @SerialName("video_width")
    var videoWidth: Int = 0

    @SerialName("preview_image_url")
    var previewImageUrl: String = ""

    @SerialName("preview_image_mime_type")
    var previewImageMimeType: String = ""

    @SerialName("preview_image_height")
    var previewImageHeight: Int = 0

    @SerialName("preview_image_width")
    var previewImageWidth: Int = 0

    @SerialName("duration")
    var duration: Float = 0f
}

@JsExport
@Serializable
class MediaStamp {
    @SerialName("url")
    var url: String = ""

    @SerialName("mime_type")
    var mimeType: String = ""

    @SerialName("height")
    var height: Int = 0

    @SerialName("width")
    var width: Int = 0
}
