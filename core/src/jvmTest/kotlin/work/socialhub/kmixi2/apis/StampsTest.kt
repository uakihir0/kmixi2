package work.socialhub.kmixi2.apis

import kotlinx.coroutines.test.runTest
import work.socialhub.kmixi2.AbstractTest
import work.socialhub.kmixi2.api.request.stamps.StampsGetStampsRequest
import kotlin.test.Test

class StampsTest : AbstractTest() {

    @Test
    fun testGetStamps() = runTest {
        val request = StampsGetStampsRequest().also {
            it.officialStampLanguage = "LANGUAGE_CODE_JP"
        }
        val response = mixi2().stamps().getStamps(request)
        println(response.data.officialStampSets.size)
    }
}
