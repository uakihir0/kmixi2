package work.socialhub.kmixi2.internal

import social.mixi.application.constant.v1.LanguageCodeOuterClass.LanguageCode
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
        val builder = Service.GetStampsRequest.newBuilder()
        request.officialStampLanguage?.let {
            builder.setOfficialStampLanguage(LanguageCode.valueOf(it))
        }
        val grpcResponse = stub.getStamps(builder.build())
        Response(StampsGetStampsResponse().also {
            it.officialStampSets = grpcResponse.officialStampSetsList
                .map { s -> s.toEntity() }.toTypedArray()
        })
    }

    override fun getStampsBlocking(
        request: StampsGetStampsRequest
    ): Response<StampsGetStampsResponse> = toBlocking { getStamps(request) }

    override suspend fun addStampToPost(
        request: StampsAddStampToPostRequest
    ): Response<StampsAddStampToPostResponse> = proceed {
        val grpcRequest = Service.AddStampToPostRequest.newBuilder()
            .setPostId(request.postId ?: "")
            .setStampId(request.stampId ?: "")
            .build()
        val grpcResponse = stub.addStampToPost(grpcRequest)
        Response(StampsAddStampToPostResponse().also {
            it.post = if (grpcResponse.hasPost()) grpcResponse.post.toEntity() else null
        })
    }

    override fun addStampToPostBlocking(
        request: StampsAddStampToPostRequest
    ): Response<StampsAddStampToPostResponse> = toBlocking { addStampToPost(request) }
}
