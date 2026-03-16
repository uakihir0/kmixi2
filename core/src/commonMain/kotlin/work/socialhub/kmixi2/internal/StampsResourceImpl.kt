package work.socialhub.kmixi2.internal

import social.mixi.application.constant.v1.LanguageCode
import social.mixi.application.service.application_api.v1.AddStampToPostRequest
import social.mixi.application.service.application_api.v1.GetStampsRequest
import work.socialhub.kmixi2.api.StampsResource
import work.socialhub.kmixi2.api.request.stamps.StampsAddStampToPostRequest
import work.socialhub.kmixi2.api.request.stamps.StampsGetStampsRequest
import work.socialhub.kmixi2.api.response.Response
import work.socialhub.kmixi2.api.response.stamps.StampsAddStampToPostResponse
import work.socialhub.kmixi2.api.response.stamps.StampsGetStampsResponse
import work.socialhub.kmixi2.grpc.WireMessageAdapter
import work.socialhub.kmixi2.util.toBlocking

class StampsResourceImpl(
    host: String,
    accessToken: String,
    authKey: String?,
) : AbstractResourceImpl(host, accessToken, authKey),
    StampsResource {

    override suspend fun getStamps(
        request: StampsGetStampsRequest
    ): Response<StampsGetStampsResponse> = proceed {
        val grpcRequest = WireMessageAdapter(
            GetStampsRequest(
                official_stamp_language = request.officialStampLanguage?.let {
                    LanguageCode.valueOf(it)
                }
            ), "GetStampsRequest"
        )
        val grpcResponse = stub.getStamps(grpcRequest, authMetadata())
        Response(StampsGetStampsResponse().also {
            it.officialStampSets = grpcResponse.wire.official_stamp_sets
                .map { s -> s.toEntity() }.toTypedArray()
        })
    }

    override fun getStampsBlocking(
        request: StampsGetStampsRequest
    ): Response<StampsGetStampsResponse> = toBlocking { getStamps(request) }

    override suspend fun addStampToPost(
        request: StampsAddStampToPostRequest
    ): Response<StampsAddStampToPostResponse> = proceed {
        val grpcRequest = WireMessageAdapter(
            AddStampToPostRequest(
                post_id = request.postId ?: "",
                stamp_id = request.stampId ?: "",
            ), "AddStampToPostRequest"
        )
        val grpcResponse = stub.addStampToPost(grpcRequest, authMetadata())
        Response(StampsAddStampToPostResponse().also {
            it.post = grpcResponse.wire.post?.toEntity()
        })
    }

    override fun addStampToPostBlocking(
        request: StampsAddStampToPostRequest
    ): Response<StampsAddStampToPostResponse> = toBlocking { addStampToPost(request) }
}
