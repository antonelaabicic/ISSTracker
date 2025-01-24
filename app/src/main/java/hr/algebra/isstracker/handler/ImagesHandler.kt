package hr.algebra.nasa.handler

import android.content.Context
import android.util.Log
import hr.algebra.nasa.factory.createGetHttpUrlConnection
import java.io.File
import java.net.HttpURLConnection
import java.nio.file.Files

fun downloadImage(context: Context, url: String): String? {
    val filename = url.substring(url.lastIndexOf(File.separator) + 1)
    val file: File = createLocalFile(context, filename)

    try {
        val con: HttpURLConnection = createGetHttpUrlConnection(url)
        Files.copy(
            con.inputStream,
            file.toPath()
        )
        return file.absolutePath
    } catch (e: Exception) {
        Log.e("IMAGES_HANDLER", e.message, e)
    }
    return null
}

fun createLocalFile(context: Context, filename: String): File {
    val dir = context.getExternalFilesDir(null)
    val file = File(dir, filename)
    if (file.exists()) file.delete()

    return file
}
