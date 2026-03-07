package work.socialhub.kmixi2.internal

import io.github.timortel.kmpgrpc.wkt.ext.toInstant
import social.mixi.application.model.v1.Event as EventProto
import social.mixi.application.model.v1.Media as MediaProto
import social.mixi.application.model.v1.Message as MessageProto
import social.mixi.application.model.v1.Post as PostProto
import social.mixi.application.model.v1.Stamp as StampProto
import social.mixi.application.model.v1.User as UserProto
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

fun UserProto.User.toEntity(): User {
    return User().also { u ->
        u.userId = user_id
        u.isDisabled = is_disabled
        u.name = name
        u.displayName = display_name
        u.profile = profile
        u.userAvatar = if (isUser_avatarSet) user_avatar.toEntity() else null
        u.visibility = visibility.name
        u.accessLevel = access_level.name
    }
}

fun UserProto.UserAvatar.toEntity(): UserAvatar {
    return UserAvatar().also { a ->
        a.largeImageUrl = large_image_url
        a.largeImageMimeType = large_image_mime_type
        a.largeImageHeight = large_image_height.toInt()
        a.largeImageWidth = large_image_width.toInt()
        a.smallImageUrl = small_image_url
        a.smallImageMimeType = small_image_mime_type
        a.smallImageHeight = small_image_height.toInt()
        a.smallImageWidth = small_image_width.toInt()
    }
}

fun PostProto.Post.toEntity(): Post {
    return Post().also { p ->
        p.postId = post_id
        p.isDeleted = is_deleted
        p.creatorId = creator_id
        p.text = text
        p.createdAt = if (isCreated_atSet) created_at.toInstant().toString() else null
        p.postMediaList = post_media_listList.map { it.toEntity() }.toTypedArray()
        p.inReplyToPostId = if (isIn_reply_to_post_idSet) in_reply_to_post_id else null
        p.postMask = if (isPost_maskSet) post_mask.toEntity() else null
        p.visibility = visibility.name
        p.accessLevel = access_level.name
        p.stamps = stampsList.map { it.toEntity() }.toTypedArray()
        p.readerStampId = if (isReader_stamp_idSet) reader_stamp_id else null
    }
}

fun PostProto.PostMedia.toEntity(): PostMedia {
    return PostMedia().also { m ->
        m.mediaType = media_type.name
        m.image = (content as? PostProto.PostMedia.Content.Image)?.image?.toEntity()
        m.video = (content as? PostProto.PostMedia.Content.Video)?.video?.toEntity()
    }
}

fun PostProto.PostMediaImage.toEntity(): PostMediaImage {
    return PostMediaImage().also { i ->
        i.largeImageUrl = large_image_url
        i.largeImageMimeType = large_image_mime_type
        i.largeImageHeight = large_image_height.toInt()
        i.largeImageWidth = large_image_width.toInt()
        i.smallImageUrl = small_image_url
        i.smallImageMimeType = small_image_mime_type
        i.smallImageHeight = small_image_height.toInt()
        i.smallImageWidth = small_image_width.toInt()
    }
}

fun PostProto.PostMediaVideo.toEntity(): PostMediaVideo {
    return PostMediaVideo().also { v ->
        v.videoUrl = video_url
        v.videoMimeType = video_mime_type
        v.videoHeight = video_height.toInt()
        v.videoWidth = video_width.toInt()
        v.previewImageUrl = preview_image_url
        v.previewImageMimeType = preview_image_mime_type
        v.previewImageHeight = preview_image_height.toInt()
        v.previewImageWidth = preview_image_width.toInt()
        v.duration = duration
    }
}

fun PostProto.PostMask.toEntity(): PostMask {
    return PostMask().also { m ->
        m.maskType = mask_type.name
        m.caption = caption
    }
}

fun PostProto.PostStamp.toEntity(): PostStamp {
    return PostStamp().also { s ->
        s.stamp = if (isStampSet) stamp.toEntity() else null
        s.count = count.toLong()
    }
}

fun MediaProto.MediaStamp.toEntity(): MediaStamp {
    return MediaStamp().also { s ->
        s.url = url
        s.mimeType = mime_type
        s.height = height.toInt()
        s.width = width.toInt()
    }
}

fun MessageProto.ChatMessage.toEntity(): ChatMessage {
    return ChatMessage().also { c ->
        c.roomId = room_id
        c.messageId = message_id
        c.creatorId = creator_id
        c.text = text
        c.createdAt = if (isCreated_atSet) created_at.toInstant().toString() else null
        c.mediaList = media_listList.map { it.toEntity() }.toTypedArray()
        c.postId = if (isPost_idSet) post_id else null
    }
}

fun MediaProto.Media.toEntity(): Media {
    return Media().also { m ->
        m.mediaType = media_type.name
        m.image = (content as? MediaProto.Media.Content.Image)?.image?.toEntity()
        m.video = (content as? MediaProto.Media.Content.Video)?.video?.toEntity()
    }
}

fun MediaProto.MediaImage.toEntity(): MediaImage {
    return MediaImage().also { i ->
        i.largeImageUrl = large_image_url
        i.largeImageMimeType = large_image_mime_type
        i.largeImageHeight = large_image_height.toInt()
        i.largeImageWidth = large_image_width.toInt()
        i.smallImageUrl = small_image_url
        i.smallImageMimeType = small_image_mime_type
        i.smallImageHeight = small_image_height.toInt()
        i.smallImageWidth = small_image_width.toInt()
    }
}

fun MediaProto.MediaVideo.toEntity(): MediaVideo {
    return MediaVideo().also { v ->
        v.videoUrl = video_url
        v.videoMimeType = video_mime_type
        v.videoHeight = video_height.toInt()
        v.videoWidth = video_width.toInt()
        v.previewImageUrl = preview_image_url
        v.previewImageMimeType = preview_image_mime_type
        v.previewImageHeight = preview_image_height.toInt()
        v.previewImageWidth = preview_image_width.toInt()
        v.duration = duration
    }
}

fun EventProto.Event.toEntity(): Event {
    return Event().also { e ->
        e.eventId = event_id
        e.eventType = event_type.name
        when (val b = body) {
            is EventProto.Event.Body.Ping_event -> {
                e.pingEvent = PingEvent()
            }
            is EventProto.Event.Body.Post_created_event -> {
                e.postCreatedEvent = b.post_created_event.toEntity()
            }
            is EventProto.Event.Body.Chat_message_received_event -> {
                e.chatMessageReceivedEvent = b.chat_message_received_event.toEntity()
            }
            else -> {}
        }
    }
}

fun EventProto.PostCreatedEvent.toEntity(): PostCreatedEvent {
    return PostCreatedEvent().also { e ->
        e.eventReasonList = event_reason_listList.map { it.name }.toTypedArray()
        e.post = if (isPostSet) post.toEntity() else null
        e.issuer = if (isIssuerSet) issuer.toEntity() else null
    }
}

fun EventProto.ChatMessageReceivedEvent.toEntity(): ChatMessageReceivedEvent {
    return ChatMessageReceivedEvent().also { e ->
        e.eventReasonList = event_reason_listList.map { it.name }.toTypedArray()
        e.message = if (isMessageSet) message.toEntity() else null
        e.issuer = if (isIssuerSet) issuer.toEntity() else null
    }
}

fun StampProto.OfficialStampSet.toEntity(): OfficialStampSet {
    return OfficialStampSet().also { s ->
        s.stampSetId = stamp_set_id
        s.name = name
        s.spriteUrl = sprite_url
        s.stamps = stampsList.map { it.toEntity() }.toTypedArray()
        s.startAt = if (isStart_atSet) start_at.toInstant().toString() else null
        s.endAt = if (isEnd_atSet) end_at.toInstant().toString() else null
        s.stampSetType = stamp_set_type.name
    }
}

fun StampProto.OfficialStamp.toEntity(): OfficialStamp {
    return OfficialStamp().also { s ->
        s.stampId = stamp_id
        s.index = index.toInt()
        s.searchTags = search_tagsList.toTypedArray()
        s.url = url
    }
}
