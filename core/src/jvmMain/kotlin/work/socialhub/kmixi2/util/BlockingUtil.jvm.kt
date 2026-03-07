package work.socialhub.kmixi2.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking

actual fun <T> toBlocking(block: suspend CoroutineScope.() -> T): T {
    return runBlocking { block() }
}
