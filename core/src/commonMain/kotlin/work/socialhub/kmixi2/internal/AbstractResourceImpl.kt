package work.socialhub.kmixi2.internal

import work.socialhub.kgrpc.metadata.Entry
import work.socialhub.kgrpc.metadata.Key
import work.socialhub.kgrpc.metadata.Metadata
import work.socialhub.kmixi2.Mixi2Exception
import work.socialhub.kmixi2.grpc.ApplicationServiceStub

abstract class AbstractResourceImpl(
    val host: String,
    val accessToken: String,
    val authKey: String? = null,
) {
    internal lateinit var stub: ApplicationServiceStub

    protected fun authMetadata(): Metadata {
        val entries = mutableListOf<Entry<*>>()
        if (accessToken.isNotEmpty()) {
            entries += Entry.Ascii(
                Key.AsciiKey("authorization"),
                setOf("Bearer $accessToken")
            )
        }
        authKey?.let {
            entries += Entry.Ascii(
                Key.AsciiKey("x-auth-key"),
                setOf(it)
            )
        }
        return Metadata.of(entries)
    }

    protected suspend fun <T> proceed(block: suspend () -> T): T {
        try {
            return block()
        } catch (e: Mixi2Exception) {
            throw e
        } catch (e: Exception) {
            throw Mixi2Exception(e)
        }
    }
}
