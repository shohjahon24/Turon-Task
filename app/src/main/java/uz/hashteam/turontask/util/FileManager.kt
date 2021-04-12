package uz.hashteam.turontask.util

import android.content.Context
import android.util.Base64
import java.io.File

class FileManager(private val context: Context) {

    val APP_FILE_DIRECTORY_PATH: String =
        "${context.filesDir}/Turon/"

    fun hasOfflineFile(url: String, dir: String, isEncrypt: Boolean): Boolean {
        return try {
            File("$APP_FILE_DIRECTORY_PATH$dir/", convertUrlToStoragePath(url, isEncrypt)).exists()
        } catch (ex: Exception) {
            false
        }
    }

    fun convertUrlToStoragePath(url: String, isEncrypt: Boolean): String {
        var filename: String = url.split("/").last()
        if (isEncrypt) {
            filename = Base64.encodeToString(filename.toByteArray(), Base64.NO_WRAP)
        }
        return filename
    }

}