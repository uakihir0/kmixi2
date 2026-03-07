package work.socialhub.kmixi2.internal

import io.github.timortel.kmpgrpc.core.metadata.Entry
import io.github.timortel.kmpgrpc.core.metadata.Key
import io.github.timortel.kmpgrpc.core.metadata.Metadata
import social.mixi.application.service.application_api.v1.Service
import work.socialhub.kmixi2.Mixi2Exception

abstract class AbstractResourceImpl(
    val host: String,
    val accessToken: String,
    val authKey: String? = null,
) {
    internal lateinit var stub: Service.ApplicationServiceStub

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
