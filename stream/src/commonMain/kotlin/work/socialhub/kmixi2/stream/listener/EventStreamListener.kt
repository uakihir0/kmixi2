package work.socialhub.kmixi2.stream.listener

import work.socialhub.kmixi2.entity.ChatMessageReceivedEvent
import work.socialhub.kmixi2.entity.PostCreatedEvent
import kotlin.js.JsExport

@JsExport
interface EventStreamListener {
    fun onPing()
    fun onPostCreated(event: PostCreatedEvent)
    fun onChatMessageReceived(event: ChatMessageReceivedEvent)
}
