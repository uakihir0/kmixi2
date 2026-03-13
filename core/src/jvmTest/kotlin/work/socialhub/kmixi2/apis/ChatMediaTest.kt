package work.socialhub.kmixi2.apis

import kotlinx.coroutines.test.runTest
import work.socialhub.khttpclient.HttpRequest
import work.socialhub.kmixi2.AbstractTest
import work.socialhub.kmixi2.api.request.chat.ChatSendChatMessageRequest
import work.socialhub.kmixi2.api.request.posts.PostsGetPostMediaStatusRequest
import work.socialhub.kmixi2.api.request.posts.PostsInitiatePostMediaUploadRequest
import kotlin.test.Test
import kotlin.test.assertNotNull

class ChatMediaTest : AbstractTest() {

    @Test
    fun testSendChatMessageWithImage() = runTest {
        val stream = javaClass.getResourceAsStream("/image/200x200.png")
        assertNotNull(stream)
        val bytes = stream.readBytes()

        val mixi2 = mixi2()

        // 1. メディアアップロードの開始
        val uploadRequest = PostsInitiatePostMediaUploadRequest().also {
            it.contentType = "image/png"
            it.dataSize = bytes.size.toDouble()
            it.mediaType = "TYPE_IMAGE"
        }
        val uploadResponse = mixi2.posts().initiatePostMediaUpload(uploadRequest)
        val mediaId = uploadResponse.data.mediaId
        val uploadUrl = uploadResponse.data.uploadUrl
        println("mediaId: $mediaId")
        println("uploadUrl: $uploadUrl")

        // 2. uploadUrl に画像をアップロード (バイナリ POST)
        val httpResponse = HttpRequest()
            .url(uploadUrl)
            .header("Authorization", "Bearer ${ACCESS_TOKEN!!}")
            .file("file", "200x200.png", bytes)
            .post()
        println("upload status: ${httpResponse.status}")

        // 3. アップロード完了を待機
        val statusRequest = PostsGetPostMediaStatusRequest().also {
            it.mediaId = mediaId
        }
        for (i in 1..30) {
            val statusResponse = mixi2.posts().getPostMediaStatus(statusRequest)
            val mediaStatus = statusResponse.data.status
            println("media status: $mediaStatus")
            if (mediaStatus != "STATUS_UPLOAD_PENDING" && mediaStatus != "STATUS_PROCESSING") {
                break
            }
            Thread.sleep(1000)
        }

        // 4. 画像付きチャットメッセージを送信 (テキストと画像は同時に送れない)
        val chatRequest = ChatSendChatMessageRequest().also {
            it.roomId = TEST_ROOM_ID
            it.mediaId = mediaId
        }
        val chatResponse = mixi2.chat().sendChatMessage(chatRequest)
        println("messageId: ${chatResponse.data.message?.messageId}")
    }
}
