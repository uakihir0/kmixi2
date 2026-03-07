package work.socialhub.kmixi2.internal

import io.grpc.CallOptions
import io.grpc.Channel
import io.grpc.ClientCall
import io.grpc.ClientInterceptor
import io.grpc.ForwardingClientCall.SimpleForwardingClientCall
import io.grpc.ManagedChannel
import io.grpc.Metadata
import io.grpc.MethodDescriptor
import io.grpc.StatusException
import social.mixi.application.service.application_api.v1.ApplicationServiceGrpcKt.ApplicationServiceCoroutineStub
import work.socialhub.kmixi2.Mixi2Exception

// FIXME: Replace with KMP gRPC library when available.
abstract class AbstractResourceImpl(
    val host: String,
    val accessToken: String,
    val authKey: String? = null,
) {
    internal lateinit var channel: ManagedChannel

    val stub: ApplicationServiceCoroutineStub by lazy {
        ApplicationServiceCoroutineStub(channel).withInterceptors(authInterceptor())
    }

    private fun authInterceptor(): ClientInterceptor {
        return object : ClientInterceptor {
            override fun <ReqT, RespT> interceptCall(
                method: MethodDescriptor<ReqT, RespT>,
                callOptions: CallOptions,
                next: Channel
            ): ClientCall<ReqT, RespT> {
                return object : SimpleForwardingClientCall<ReqT, RespT>(
                    next.newCall(method, callOptions)
                ) {
                    override fun start(
                        responseListener: Listener<RespT>,
                        headers: Metadata
                    ) {
                        if (accessToken.isNotEmpty()) {
                            headers.put(
                                Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER),
                                "Bearer $accessToken"
                            )
                        }
                        authKey?.let {
                            headers.put(
                                Metadata.Key.of("x-auth-key", Metadata.ASCII_STRING_MARSHALLER),
                                it
                            )
                        }
                        super.start(responseListener, headers)
                    }
                }
            }
        }
    }

    protected suspend fun <T> proceed(block: suspend () -> T): T {
        try {
            return block()
        } catch (e: StatusException) {
            throw Mixi2Exception("gRPC error: ${e.status.code} - ${e.status.description}")
        } catch (e: Mixi2Exception) {
            throw e
        } catch (e: Exception) {
            throw Mixi2Exception(e)
        }
    }
}
