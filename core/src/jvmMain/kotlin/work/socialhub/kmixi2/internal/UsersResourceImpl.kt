package work.socialhub.kmixi2.internal

import work.socialhub.kmixi2.api.UsersResource
import work.socialhub.kmixi2.api.request.users.UsersGetUsersRequest
import work.socialhub.kmixi2.api.response.Response
import work.socialhub.kmixi2.api.response.users.UsersGetUsersResponse
import work.socialhub.kmixi2.util.toBlocking

class UsersResourceImpl(
    host: String,
    accessToken: String,
    authKey: String?,
) : AbstractResourceImpl(host, accessToken, authKey),
    UsersResource {

    override suspend fun getUsers(
        request: UsersGetUsersRequest
    ): Response<UsersGetUsersResponse> {
        // TODO: Call ApplicationService.GetUsers via gRPC stub
        //  val grpcRequest = GetUsersRequest.newBuilder()
        //      .addAllUserIdList(request.userIdList?.toList() ?: emptyList())
        //      .build()
        //  val grpcResponse = applicationServiceStub.getUsers(grpcRequest)
        //  return Response(grpcResponse.toModel())
        TODO("gRPC implementation pending")
    }

    override fun getUsersBlocking(
        request: UsersGetUsersRequest
    ): Response<UsersGetUsersResponse> = toBlocking { getUsers(request) }
}
