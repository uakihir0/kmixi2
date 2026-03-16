package work.socialhub.kmixi2.grpc

import com.squareup.wire.Message
import com.squareup.wire.ProtoAdapter
import work.socialhub.kgrpc.message.Message as KGrpcMessage
import work.socialhub.kgrpc.message.MessageCompanion

class WireMessageAdapter<T : Message<T, *>>(
    val wire: T,
    override val fullName: String
) : KGrpcMessage {
    override val requiredSize: Int get() = wire.encode().size
    override val isInitialized: Boolean = true
    override fun serialize(): ByteArray = wire.encode()
}

class WireCompanionAdapter<T : Message<T, *>>(
    override val fullName: String,
    private val adapter: ProtoAdapter<T>
) : MessageCompanion<WireMessageAdapter<T>> {
    override fun deserialize(data: ByteArray): WireMessageAdapter<T> {
        return WireMessageAdapter(adapter.decode(data), fullName)
    }
}
