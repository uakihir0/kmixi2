package work.socialhub.kmixi2.internal

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
    ): Response<StampsGetStampsResponse> {
        // TODO: Call ApplicationService.GetStamps via gRPC stub
        TODO("gRPC implementation pending")
    }

    override fun getStampsBlocking(
        request: StampsGetStampsRequest
    ): Response<StampsGetStampsResponse> = toBlocking { getStamps(request) }

    override suspend fun addStampToPost(
        request: StampsAddStampToPostRequest
    ): Response<StampsAddStampToPostResponse> {
        // TODO: Call ApplicationService.AddStampToPost via gRPC stub
        TODO("gRPC implementation pending")
    }

    override fun addStampToPostBlocking(
        request: StampsAddStampToPostRequest
    ): Response<StampsAddStampToPostResponse> = toBlocking { addStampToPost(request) }
}
