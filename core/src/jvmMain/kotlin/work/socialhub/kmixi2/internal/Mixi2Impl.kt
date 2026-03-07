package work.socialhub.kmixi2.internal

import work.socialhub.kmixi2.Mixi2
import work.socialhub.kmixi2.api.AuthResource
import work.socialhub.kmixi2.api.ChatResource
import work.socialhub.kmixi2.api.PostsResource
import work.socialhub.kmixi2.api.StampsResource
import work.socialhub.kmixi2.api.UsersResource

class Mixi2Impl(
    private val host: String,
    private val accessToken: String,
    private val authKey: String?,
) : Mixi2 {

    private val users: UsersResource = UsersResourceImpl(host, accessToken, authKey)
    private val posts: PostsResource = PostsResourceImpl(host, accessToken, authKey)
    private val chat: ChatResource = ChatResourceImpl(host, accessToken, authKey)
    private val stamps: StampsResource = StampsResourceImpl(host, accessToken, authKey)
    private val auth: AuthResource = AuthResourceImpl()

    override fun users() = users
    override fun posts() = posts
    override fun chat() = chat
    override fun stamps() = stamps
    override fun auth() = auth
    override fun host() = host
}
