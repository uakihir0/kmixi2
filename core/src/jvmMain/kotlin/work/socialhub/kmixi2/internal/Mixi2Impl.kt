package work.socialhub.kmixi2.internal

import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import work.socialhub.kmixi2.Mixi2
import work.socialhub.kmixi2.api.AuthResource
import work.socialhub.kmixi2.api.ChatResource
import work.socialhub.kmixi2.api.PostsResource
import work.socialhub.kmixi2.api.StampsResource
import work.socialhub.kmixi2.api.UsersResource
import java.util.concurrent.TimeUnit

class Mixi2Impl(
    private val host: String,
    internal val accessToken: String,
    internal val authKey: String?,
) : Mixi2 {

    internal val channel: ManagedChannel by lazy {
        ManagedChannelBuilder
            .forTarget(host)
            .useTransportSecurity()
            .build()
    }

    private val users: UsersResource = UsersResourceImpl(host, accessToken, authKey)
        .also { it.channel = channel }
    private val posts: PostsResource = PostsResourceImpl(host, accessToken, authKey)
        .also { it.channel = channel }
    private val chat: ChatResource = ChatResourceImpl(host, accessToken, authKey)
        .also { it.channel = channel }
    private val stamps: StampsResource = StampsResourceImpl(host, accessToken, authKey)
        .also { it.channel = channel }
    private val auth: AuthResource = AuthResourceImpl()

    override fun users() = users
    override fun posts() = posts
    override fun chat() = chat
    override fun stamps() = stamps
    override fun auth() = auth
    override fun host() = host

    fun close() {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS)
    }
}
