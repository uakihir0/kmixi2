package work.socialhub.kmixi2.internal

import kotlin.time.Instant
import social.mixi.application.model.v1.EventOuterClass
import social.mixi.application.model.v1.MediaOuterClass
import social.mixi.application.model.v1.Message
import social.mixi.application.model.v1.PostOuterClass
import social.mixi.application.model.v1.Stamp
import social.mixi.application.model.v1.UserOuterClass
import work.socialhub.kmixi2.entity.ChatMessage
import work.socialhub.kmixi2.entity.ChatMessageReceivedEvent
import work.socialhub.kmixi2.entity.Event
import work.socialhub.kmixi2.entity.Media
import work.socialhub.kmixi2.entity.MediaImage
import work.socialhub.kmixi2.entity.MediaStamp
import work.socialhub.kmixi2.entity.MediaVideo
import work.socialhub.kmixi2.entity.OfficialStamp
import work.socialhub.kmixi2.entity.OfficialStampSet
import work.socialhub.kmixi2.entity.PingEvent
import work.socialhub.kmixi2.entity.Post
import work.socialhub.kmixi2.entity.PostCreatedEvent
import work.socialhub.kmixi2.entity.PostMask
import work.socialhub.kmixi2.entity.PostMedia
import work.socialhub.kmixi2.entity.PostMediaImage
import work.socialhub.kmixi2.entity.PostMediaVideo
import work.socialhub.kmixi2.entity.PostStamp
import work.socialhub.kmixi2.entity.User
import work.socialhub.kmixi2.entity.UserAvatar

fun com.google.protobuf.Timestamp.toIsoString(): String {
    return Instant.fromEpochSeconds(seconds, nanos).toString()
}

fun UserOuterClass.User.toEntity(): User {
    return User().also { u ->
        u.userId = userId
        u.isDisabled = isDisabled
        u.name = name
        u.displayName = displayName
        u.profile = profile
        u.userAvatar = if (hasUserAvatar()) userAvatar.toEntity() else null
        u.visibility = visibility.name
        u.accessLevel = accessLevel.name
    }
}

fun UserOuterClass.UserAvatar.toEntity(): UserAvatar {
    return UserAvatar().also { a ->
        a.largeImageUrl = largeImageUrl
        a.largeImageMimeType = largeImageMimeType
        a.largeImageHeight = largeImageHeight
        a.largeImageWidth = largeImageWidth
        a.smallImageUrl = smallImageUrl
        a.smallImageMimeType = smallImageMimeType
        a.smallImageHeight = smallImageHeight
        a.smallImageWidth = smallImageWidth
    }
}

fun PostOuterClass.Post.toEntity(): Post {
    return Post().also { p ->
        p.postId = postId
        p.isDeleted = isDeleted
        p.creatorId = creatorId
        p.text = text
        p.createdAt = if (hasCreatedAt()) createdAt.toIsoString() else null
        p.postMediaList = postMediaListList.map { it.toEntity() }.toTypedArray()
        p.inReplyToPostId = if (hasInReplyToPostId()) inReplyToPostId else null
        p.postMask = if (hasPostMask()) postMask.toEntity() else null
        p.visibility = visibility.name
        p.accessLevel = accessLevel.name
        p.stamps = stampsList.map { it.toEntity() }.toTypedArray()
        p.readerStampId = if (hasReaderStampId()) readerStampId else null
    }
}

fun PostOuterClass.PostMedia.toEntity(): PostMedia {
    return PostMedia().also { m ->
        m.mediaType = mediaType.name
        m.image = if (hasImage()) image.toEntity() else null
        m.video = if (hasVideo()) video.toEntity() else null
    }
}

fun PostOuterClass.PostMediaImage.toEntity(): PostMediaImage {
    return PostMediaImage().also { i ->
        i.largeImageUrl = largeImageUrl
        i.largeImageMimeType = largeImageMimeType
        i.largeImageHeight = largeImageHeight
        i.largeImageWidth = largeImageWidth
        i.smallImageUrl = smallImageUrl
        i.smallImageMimeType = smallImageMimeType
        i.smallImageHeight = smallImageHeight
        i.smallImageWidth = smallImageWidth
    }
}

fun PostOuterClass.PostMediaVideo.toEntity(): PostMediaVideo {
    return PostMediaVideo().also { v ->
        v.videoUrl = videoUrl
        v.videoMimeType = videoMimeType
        v.videoHeight = videoHeight
        v.videoWidth = videoWidth
        v.previewImageUrl = previewImageUrl
        v.previewImageMimeType = previewImageMimeType
        v.previewImageHeight = previewImageHeight
        v.previewImageWidth = previewImageWidth
        v.duration = duration
    }
}

fun PostOuterClass.PostMask.toEntity(): PostMask {
    return PostMask().also { m ->
        m.maskType = maskType.name
        m.caption = caption
    }
}

fun PostOuterClass.PostStamp.toEntity(): PostStamp {
    return PostStamp().also { s ->
        s.stamp = if (hasStamp()) stamp.toEntity() else null
        s.count = count
    }
}

fun MediaOuterClass.MediaStamp.toEntity(): MediaStamp {
    return MediaStamp().also { s ->
        s.url = url
        s.mimeType = mimeType
        s.height = height
        s.width = width
    }
}

fun Message.ChatMessage.toEntity(): ChatMessage {
    return ChatMessage().also { c ->
        c.roomId = roomId
        c.messageId = messageId
        c.creatorId = creatorId
        c.text = text
        c.createdAt = if (hasCreatedAt()) createdAt.toIsoString() else null
        c.mediaList = mediaListList.map { it.toEntity() }.toTypedArray()
        c.postId = if (hasPostId()) postId else null
    }
}

fun MediaOuterClass.Media.toEntity(): Media {
    return Media().also { m ->
        m.mediaType = mediaType.name
        m.image = if (hasImage()) image.toEntity() else null
        m.video = if (hasVideo()) video.toEntity() else null
    }
}

fun MediaOuterClass.MediaImage.toEntity(): MediaImage {
    return MediaImage().also { i ->
        i.largeImageUrl = largeImageUrl
        i.largeImageMimeType = largeImageMimeType
        i.largeImageHeight = largeImageHeight
        i.largeImageWidth = largeImageWidth
        i.smallImageUrl = smallImageUrl
        i.smallImageMimeType = smallImageMimeType
        i.smallImageHeight = smallImageHeight
        i.smallImageWidth = smallImageWidth
    }
}

fun MediaOuterClass.MediaVideo.toEntity(): MediaVideo {
    return MediaVideo().also { v ->
        v.videoUrl = videoUrl
        v.videoMimeType = videoMimeType
        v.videoHeight = videoHeight
        v.videoWidth = videoWidth
        v.previewImageUrl = previewImageUrl
        v.previewImageMimeType = previewImageMimeType
        v.previewImageHeight = previewImageHeight
        v.previewImageWidth = previewImageWidth
        v.duration = duration
    }
}

fun EventOuterClass.Event.toEntity(): Event {
    return Event().also { e ->
        e.eventId = eventId
        e.eventType = eventType.name
        e.pingEvent = if (hasPingEvent()) PingEvent() else null
        e.postCreatedEvent = if (hasPostCreatedEvent()) postCreatedEvent.toEntity() else null
        e.chatMessageReceivedEvent = if (hasChatMessageReceivedEvent()) chatMessageReceivedEvent.toEntity() else null
    }
}

fun EventOuterClass.PostCreatedEvent.toEntity(): PostCreatedEvent {
    return PostCreatedEvent().also { e ->
        e.eventReasonList = eventReasonListList.map { it.name }.toTypedArray()
        e.post = if (hasPost()) post.toEntity() else null
        e.issuer = if (hasIssuer()) issuer.toEntity() else null
    }
}

fun EventOuterClass.ChatMessageReceivedEvent.toEntity(): ChatMessageReceivedEvent {
    return ChatMessageReceivedEvent().also { e ->
        e.eventReasonList = eventReasonListList.map { it.name }.toTypedArray()
        e.message = if (hasMessage()) message.toEntity() else null
        e.issuer = if (hasIssuer()) issuer.toEntity() else null
    }
}

fun Stamp.OfficialStampSet.toEntity(): OfficialStampSet {
    return OfficialStampSet().also { s ->
        s.stampSetId = stampSetId
        s.name = name
        s.spriteUrl = spriteUrl
        s.stamps = stampsList.map { it.toEntity() }.toTypedArray()
        s.startAt = if (hasStartAt()) startAt.toIsoString() else null
        s.endAt = if (hasEndAt()) endAt.toIsoString() else null
        s.stampSetType = stampSetType.name
    }
}

fun Stamp.OfficialStamp.toEntity(): OfficialStamp {
    return OfficialStamp().also { s ->
        s.stampId = stampId
        s.index = index
        s.searchTags = searchTagsList.toTypedArray()
        s.url = url
    }
}
