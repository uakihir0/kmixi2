package work.socialhub.kmixi2.internal

import social.mixi.application.service.application_api.v1.GetUsersRequest
import work.socialhub.kmixi2.api.UsersResource
import work.socialhub.kmixi2.api.request.users.UsersGetUsersRequest
import work.socialhub.kmixi2.api.response.Response
import work.socialhub.kmixi2.api.response.users.UsersGetUsersResponse
import work.socialhub.kmixi2.grpc.WireMessageAdapter
import work.socialhub.kmixi2.util.toBlocking

class UsersResourceImpl(
    host: String,
    accessToken: String,
    authKey: String?,
) : AbstractResourceImpl(host, accessToken, authKey),
    UsersResource {

    override suspend fun getUsers(
        request: UsersGetUsersRequest
    ): Response<UsersGetUsersResponse> = proceed {
        val grpcRequest = WireMessageAdapter(
            GetUsersRequest(
                user_id_list = request.userIdList?.toList() ?: emptyList()
            ), "GetUsersRequest"
        )
        val grpcResponse = stub.getUsers(grpcRequest, authMetadata())
        Response(UsersGetUsersResponse().also {
            it.users = grpcResponse.wire.users.map { u -> u.toEntity() }.toTypedArray()
        })
    }

    override fun getUsersBlocking(
        request: UsersGetUsersRequest
    ): Response<UsersGetUsersResponse> = toBlocking { getUsers(request) }
}
