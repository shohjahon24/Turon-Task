package uz.hashteam.turontask

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.greenrobot.eventbus.EventBus
import uz.hashteam.turontask.data.video.Video
import uz.hashteam.turontask.data.video.VideoX
import uz.hashteam.turontask.util.getJsonDataFromAsset


class MainActivity : AppCompatActivity() {

    private val REQUEST_READ_STORAGE = 1
    private val REQUEST_WRITE_STORAGE = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermissions()
    }

    private fun requestPermissions() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_READ_STORAGE
                )
            }
        } else if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    REQUEST_WRITE_STORAGE
                )
            }
        }
    }

    fun getVideos(): List<VideoX> {
        val gson = Gson()
        val data: Video
        var list: ArrayList<VideoX> = ArrayList()
        val jsonDataString = getJsonDataFromAsset(this, "video.json")
        data = gson.fromJson(jsonDataString, object : TypeToken<Video>() {}.type)
        data.videos.forEach {
            val d = it.sources[0].split(".")
            val ext = d[d.size - 1]
            if (ext == "mp4")
                list.add(it)
        }
        Log.d("TAG", "getVideos: ${list.size}")
        return list
    }

}