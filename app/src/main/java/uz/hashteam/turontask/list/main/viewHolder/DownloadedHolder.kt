package uz.hashteam.turontask.list.main.viewHolder

import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.downloader.*
import kotlinx.coroutines.*
import uz.hashteam.turontask.R
import uz.hashteam.turontask.data.video.Status
import uz.hashteam.turontask.data.video.VideoX
import uz.hashteam.turontask.databinding.ItemVideoBinding
import uz.hashteam.turontask.util.FileManager
import uz.hashteam.turontask.util.Prefs
import uz.hashteam.turontask.util.getFileSize
import java.net.URL
import java.util.concurrent.TimeUnit

class DownloadedHolder(
    private val binding: ItemVideoBinding,
    private val fileManager: FileManager
) :
    RecyclerView.ViewHolder(binding.root) {

    var listener: ((VideoX) -> Unit)? = null

    lateinit var url: String

    private lateinit var data: VideoX

    fun bind(video: VideoX) {
        this.data = video
        url = video.sources[0]
        binding.let {
            it.llDownload.visibility = View.GONE
            it.description.text = video.description
            Glide.with(it.root.context).load(video.thumb).into(it.thumbnail)
            it.title.text = video.title
            videoLength()
            it.play.setOnClickListener {
                open()
            }
        }
    }

    private fun videoLength() {
        val mp = MediaPlayer.create(
            itemView.context, Uri.parse(
                fileManager.APP_FILE_DIRECTORY_PATH + "video/" + fileManager.convertUrlToStoragePath(
                    url,
                    true
                )
            )
        )
        try {
            val duration = mp.duration.toLong()
            mp.release()
            binding.size.text = "%d:%02d".format(
                TimeUnit.MILLISECONDS.toMinutes(duration),
                TimeUnit.MILLISECONDS.toSeconds(duration) % 60
            )
        } catch (x: Exception) {

        }
    }

    private fun open() {
        val data = this.data.copy()
        data.sources =
            arrayListOf(
                fileManager.APP_FILE_DIRECTORY_PATH + "video/" + fileManager.convertUrlToStoragePath(
                    url,
                    true
                )
            )
        listener?.invoke(data)
    }
}