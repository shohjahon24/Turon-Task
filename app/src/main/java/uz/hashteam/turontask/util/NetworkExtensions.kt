package uz.hashteam.turontask.util

import java.net.URL

//
//fun Context.isOnline(): Boolean {
//    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//    val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
//    return activeNetwork?.isConnectedOrConnecting == true
//}

fun URL.getFileSize(): Int? {
    return try {
        openConnection().contentLength
    } catch (x: Exception) {
        null
    }
}