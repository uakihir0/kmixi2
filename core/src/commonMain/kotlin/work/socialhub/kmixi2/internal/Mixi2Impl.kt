package work.socialhub.kmixi2.internal

import io.github.timortel.kmpgrpc.core.Channel
import social.mixi.application.service.application_api.v1.Service
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

    internal val channel: Channel by lazy {
        val parts = host.split(":")
        val hostname = parts[0]
        val port = if (parts.size > 1) parts[1].toInt() else 443
        Channel.Builder.Companion.forAddress(hostname, port).build()
    }

    private val stub: Service.ApplicationServiceStub by lazy {
        Service.ApplicationServiceStub(channel)
    }

    private val users: UsersResource = UsersResourceImpl(host, accessToken, authKey)
        .also { it.stub = stub }
    private val posts: PostsResource = PostsResourceImpl(host, accessToken, authKey)
        .also { it.stub = stub }
    private val chat: ChatResource = ChatResourceImpl(host, accessToken, authKey)
        .also { it.stub = stub }
    private val stamps: StampsResource = StampsResourceImpl(host, accessToken, authKey)
        .also { it.stub = stub }
    private val auth: AuthResource = AuthResourceImpl()

    override fun users() = users
    override fun posts() = posts
    override fun chat() = chat
    override fun stamps() = stamps
    override fun auth() = auth
    override fun host() = host
    override fun accessToken() = accessToken
    override fun authKey() = authKey

    suspend fun close() {
        channel.shutdown()
    }
}
