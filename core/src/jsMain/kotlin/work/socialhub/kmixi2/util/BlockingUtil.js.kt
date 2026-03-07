package work.socialhub.kmixi2.util

import kotlinx.coroutines.CoroutineScope

actual fun <T> toBlocking(block: suspend CoroutineScope.() -> T): T {
    throw UnsupportedOperationException(
        "Blocking calls are not supported on JS. Use suspend functions instead."
    )
}
