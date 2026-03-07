package work.socialhub.kmixi2.internal

import social.mixi.application.constant.v1.LanguageCode
import social.mixi.application.service.application_api.v1.Service
import work.socialhub.kmixi2.api.StampsResource
import work.socialhub.kmixi2.api.request.stamps.StampsAddStampToPostRequest
import work.socialhub.kmixi2.api.request.stamps.StampsGetStampsRequest
import work.socialhub.kmixi2.api.response.Response
import work.socialhub.kmixi2.api.response.stamps.StampsAddStampToPostResponse
import work.socialhub.kmixi2.api.response.stamps.StampsGetStampsResponse
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
        val grpcRequest = Service.GetStampsRequest(
            official_stamp_language = request.officialStampLanguage?.let {
                LanguageCode.LanguageCode.valueOf(it)
            }
        )
        val grpcResponse = stub.GetStamps(grpcRequest, authMetadata())
        Response(StampsGetStampsResponse().also {
            it.officialStampSets = grpcResponse.official_stamp_setsList
                .map { s -> s.toEntity() }.toTypedArray()
        })
    }

    override fun getStampsBlocking(
        request: StampsGetStampsRequest
    ): Response<StampsGetStampsResponse> = toBlocking { getStamps(request) }

    override suspend fun addStampToPost(
        request: StampsAddStampToPostRequest
    ): Response<StampsAddStampToPostResponse> = proceed {
        val grpcRequest = Service.AddStampToPostRequest(
            post_id = request.postId ?: "",
            stamp_id = request.stampId ?: "",
        )
        val grpcResponse = stub.AddStampToPost(grpcRequest, authMetadata())
        Response(StampsAddStampToPostResponse().also {
            it.post = if (grpcResponse.isPostSet) grpcResponse.post.toEntity() else null
        })
    }

    override fun addStampToPostBlocking(
        request: StampsAddStampToPostRequest
    ): Response<StampsAddStampToPostResponse> = toBlocking { addStampToPost(request) }
}
