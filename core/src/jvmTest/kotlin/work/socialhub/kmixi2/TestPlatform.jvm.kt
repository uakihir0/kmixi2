package work.socialhub.kmixi2

import java.io.File

actual fun getEnvVar(name: String): String? {
    return System.getenv(name) ?: System.getProperty(name)
}

actual fun readFileText(path: String): String? {
    return try {
        File(path).readText()
    } catch (e: Exception) {
        null
    }
}
