package work.socialhub.kmixi2.internal

import com.squareup.wire.Instant
import social.mixi.application.model.v1.ChatMessage as ChatMessageProto
import social.mixi.application.model.v1.ChatMessageReceivedEvent as ChatMessageReceivedEventProto
import social.mixi.application.model.v1.Event as EventProto
import social.mixi.application.model.v1.Media as MediaProto
import social.mixi.application.model.v1.MediaImage as MediaImageProto
import social.mixi.application.model.v1.MediaStamp as MediaStampProto
import social.mixi.application.model.v1.MediaVideo as MediaVideoProto
import social.mixi.application.model.v1.OfficialStamp as OfficialStampProto
import social.mixi.application.model.v1.OfficialStampSet as OfficialStampSetProto
import social.mixi.application.model.v1.PingEvent as PingEventProto
import social.mixi.application.model.v1.Post as PostProto
import social.mixi.application.model.v1.PostCreatedEvent as PostCreatedEventProto
import social.mixi.application.model.v1.PostMask as PostMaskProto
import social.mixi.application.model.v1.PostMedia as PostMediaProto
import social.mixi.application.model.v1.PostMediaImage as PostMediaImageProto
import social.mixi.application.model.v1.PostMediaVideo as PostMediaVideoProto
import social.mixi.application.model.v1.PostStamp as PostStampProto
import social.mixi.application.model.v1.User as UserProto
import social.mixi.application.model.v1.UserAvatar as UserAvatarProto
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

private fun Instant.toInstantString(): String {
    return toString()
}

fun UserProto.toEntity(): User {
    return User().also { u ->
        u.userId = user_id
        u.isDisabled = is_disabled
        u.name = name
        u.displayName = display_name
        u.profile = profile
        u.userAvatar = user_avatar?.toEntity()
        u.visibility = visibility.name
        u.accessLevel = access_level.name
    }
}

fun UserAvatarProto.toEntity(): UserAvatar {
    return UserAvatar().also { a ->
        a.largeImageUrl = large_image_url
        a.largeImageMimeType = large_image_mime_type
        a.largeImageHeight = large_image_height
        a.largeImageWidth = large_image_width
        a.smallImageUrl = small_image_url
        a.smallImageMimeType = small_image_mime_type
        a.smallImageHeight = small_image_height
        a.smallImageWidth = small_image_width
    }
}

fun PostProto.toEntity(): Post {
    return Post().also { p ->
        p.postId = post_id
        p.isDeleted = is_deleted
        p.creatorId = creator_id
        p.text = text
        p.createdAt = created_at?.toInstantString()
        p.postMediaList = post_media_list.map { it.toEntity() }.toTypedArray()
        p.inReplyToPostId = in_reply_to_post_id
        p.postMask = post_mask?.toEntity()
        p.visibility = visibility.name
        p.accessLevel = access_level.name
        p.stamps = stamps.map { it.toEntity() }.toTypedArray()
        p.readerStampId = reader_stamp_id
    }
}

fun PostMediaProto.toEntity(): PostMedia {
    return PostMedia().also { m ->
        m.mediaType = media_type.name
        m.image = image?.toEntity()
        m.video = video?.toEntity()
    }
}

fun PostMediaImageProto.toEntity(): PostMediaImage {
    return PostMediaImage().also { i ->
        i.largeImageUrl = large_image_url
        i.largeImageMimeType = large_image_mime_type
        i.largeImageHeight = large_image_height
        i.largeImageWidth = large_image_width
        i.smallImageUrl = small_image_url
        i.smallImageMimeType = small_image_mime_type
        i.smallImageHeight = small_image_height
        i.smallImageWidth = small_image_width
    }
}

fun PostMediaVideoProto.toEntity(): PostMediaVideo {
    return PostMediaVideo().also { v ->
        v.videoUrl = video_url
        v.videoMimeType = video_mime_type
        v.videoHeight = video_height
        v.videoWidth = video_width
        v.previewImageUrl = preview_image_url
        v.previewImageMimeType = preview_image_mime_type
        v.previewImageHeight = preview_image_height
        v.previewImageWidth = preview_image_width
        v.duration = duration
    }
}

fun PostMaskProto.toEntity(): PostMask {
    return PostMask().also { m ->
        m.maskType = mask_type.name
        m.caption = caption
    }
}

fun PostStampProto.toEntity(): PostStamp {
    return PostStamp().also { s ->
        s.stamp = stamp?.toEntity()
        s.count = count.toInt()
    }
}

fun MediaStampProto.toEntity(): MediaStamp {
    return MediaStamp().also { s ->
        s.url = url
        s.mimeType = mime_type
        s.height = height
        s.width = width
    }
}

fun ChatMessageProto.toEntity(): ChatMessage {
    return ChatMessage().also { c ->
        c.roomId = room_id
        c.messageId = message_id
        c.creatorId = creator_id
        c.text = text
        c.createdAt = created_at?.toInstantString()
        c.mediaList = media_list.map { it.toEntity() }.toTypedArray()
        c.postId = post_id
    }
}

fun MediaProto.toEntity(): Media {
    return Media().also { m ->
        m.mediaType = media_type.name
        m.image = image?.toEntity()
        m.video = video?.toEntity()
    }
}

fun MediaImageProto.toEntity(): MediaImage {
    return MediaImage().also { i ->
        i.largeImageUrl = large_image_url
        i.largeImageMimeType = large_image_mime_type
        i.largeImageHeight = large_image_height
        i.largeImageWidth = large_image_width
        i.smallImageUrl = small_image_url
        i.smallImageMimeType = small_image_mime_type
        i.smallImageHeight = small_image_height
        i.smallImageWidth = small_image_width
    }
}

fun MediaVideoProto.toEntity(): MediaVideo {
    return MediaVideo().also { v ->
        v.videoUrl = video_url
        v.videoMimeType = video_mime_type
        v.videoHeight = video_height
        v.videoWidth = video_width
        v.previewImageUrl = preview_image_url
        v.previewImageMimeType = preview_image_mime_type
        v.previewImageHeight = preview_image_height
        v.previewImageWidth = preview_image_width
        v.duration = duration
    }
}

fun EventProto.toEntity(): Event {
    return Event().also { e ->
        e.eventId = event_id
        e.eventType = event_type.name
        when {
            ping_event != null -> {
                e.pingEvent = PingEvent()
            }
            post_created_event != null -> {
                e.postCreatedEvent = post_created_event!!.toEntity()
            }
            chat_message_received_event != null -> {
                e.chatMessageReceivedEvent = chat_message_received_event!!.toEntity()
            }
        }
    }
}

fun PostCreatedEventProto.toEntity(): PostCreatedEvent {
    return PostCreatedEvent().also { e ->
        e.eventReasonList = event_reason_list.map { it.name }.toTypedArray()
        e.post = post?.toEntity()
        e.issuer = issuer?.toEntity()
    }
}

fun ChatMessageReceivedEventProto.toEntity(): ChatMessageReceivedEvent {
    return ChatMessageReceivedEvent().also { e ->
        e.eventReasonList = event_reason_list.map { it.name }.toTypedArray()
        e.message = message?.toEntity()
        e.issuer = issuer?.toEntity()
    }
}

fun OfficialStampSetProto.toEntity(): OfficialStampSet {
    return OfficialStampSet().also { s ->
        s.stampSetId = stamp_set_id
        s.name = name
        s.spriteUrl = sprite_url
        s.stamps = stamps.map { it.toEntity() }.toTypedArray()
        s.startAt = start_at?.toInstantString()
        s.endAt = end_at?.toInstantString()
        s.stampSetType = stamp_set_type.name
    }
}

fun OfficialStampProto.toEntity(): OfficialStamp {
    return OfficialStamp().also { s ->
        s.stampId = stamp_id
        s.index = index
        s.searchTags = search_tags.toTypedArray()
        s.url = url
    }
}
