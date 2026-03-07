package work.socialhub.kmixi2

import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.toKString
import platform.posix.fclose
import platform.posix.fgets
import platform.posix.fopen
import platform.posix.getenv

@OptIn(ExperimentalForeignApi::class)
actual fun getEnvVar(name: String): String? {
    return getenv(name)?.toKString()
}

@OptIn(ExperimentalForeignApi::class)
actual fun readFileText(path: String): String? {
    val file = fopen(path, "r") ?: return null
    val builder = StringBuilder()
    try {
        memScoped {
            val buffer = allocArray<ByteVar>(4096)
            while (true) {
                val line = fgets(buffer, 4096, file) ?: break
                builder.append(line.toKString())
            }
        }
    } finally {
        fclose(file)
    }
    return builder.toString()
}
