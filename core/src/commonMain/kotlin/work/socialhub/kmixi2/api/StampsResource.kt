package work.socialhub.kmixi2.api

import work.socialhub.kmixi2.api.request.stamps.StampsAddStampToPostRequest
import work.socialhub.kmixi2.api.request.stamps.StampsGetStampsRequest
import work.socialhub.kmixi2.api.response.Response
import work.socialhub.kmixi2.api.response.stamps.StampsAddStampToPostResponse
import work.socialhub.kmixi2.api.response.stamps.StampsGetStampsResponse
import kotlin.js.JsExport

@JsExport
interface StampsResource {

    suspend fun getStamps(
        request: StampsGetStampsRequest
    ): Response<StampsGetStampsResponse>

    @JsExport.Ignore
    fun getStampsBlocking(
        request: StampsGetStampsRequest
    ): Response<StampsGetStampsResponse>

    suspend fun addStampToPost(
        request: StampsAddStampToPostRequest
    ): Response<StampsAddStampToPostResponse>

    @JsExport.Ignore
    fun addStampToPostBlocking(
        request: StampsAddStampToPostRequest
    ): Response<StampsAddStampToPostResponse>
}
