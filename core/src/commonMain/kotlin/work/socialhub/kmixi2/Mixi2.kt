package work.socialhub.kmixi2

import work.socialhub.kmixi2.api.AuthResource
import work.socialhub.kmixi2.api.ChatResource
import work.socialhub.kmixi2.api.PostsResource
import work.socialhub.kmixi2.api.StampsResource
import work.socialhub.kmixi2.api.UsersResource
import kotlin.js.JsExport

@JsExport
interface Mixi2 {
    fun users(): UsersResource
    fun posts(): PostsResource
    fun chat(): ChatResource
    fun stamps(): StampsResource
    fun auth(): AuthResource
    fun host(): String
}
