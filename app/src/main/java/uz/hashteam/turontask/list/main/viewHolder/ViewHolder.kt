package uz.hashteam.turontask.list.main.viewHolder

import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.downloader.*
import kotlinx.coroutines.*
import org.koin.experimental.property.inject
import uz.hashteam.turontask.R
import uz.hashteam.turontask.data.video.Status
import uz.hashteam.turontask.data.video.VideoX
import uz.hashteam.turontask.databinding.ItemVideoBinding
import uz.hashteam.turontask.util.FileManager
import uz.hashteam.turontask.util.Prefs
import uz.hashteam.turontask.util.getFileSize
import java.net.URL
import java.util.concurrent.TimeUnit
import kotlin.math.log

class ViewHolder(
    private val prefs: Prefs,
    private val binding: ItemVideoBinding,
    private val fileManager: FileManager
) :
    RecyclerView.ViewHolder(binding.root) {

    var listener: ((VideoX) -> Unit)? = null

    lateinit var url: String

    private lateinit var data: VideoX

    private var downloadId = 0

    fun bind(video: VideoX) {
        Log.d("TAG", "bind: ")
        this.data = video
        url = video.sources[0]
        binding.let {
            it.description.text = video.description
            Glide.with(it.root.context).load(video.thumb).into(it.thumbnail)
            it.title.text = video.title
            data.status =
                if (fileManager.hasOfflineFile(url, "video", true)) {
                    binding.llDownload.visibility = View.GONE
                    binding.pb.visibility = View.INVISIBLE
                    videoLength()
                    2
                } else {
                    binding.pb.visibility = View.INVISIBLE
                    val fileSize = prefs.get("file${data.title}", 0)
                    if (fileSize == 0)
                        getFileSizeFromServer("file${data.title}")
                    else {
                        val width: Float = ((fileSize.toFloat() / 1024) / 1024)
                        val text = "%.2f Mb".format(width)
                        binding.size.text = text
                    }
                    binding.llDownload.visibility = View.VISIBLE
                    0
                }
            it.llDownload.setOnClickListener {
                when (data.status) {
                    Status.UNDOWNLOAD -> download()
                    Status.DOWNLOADING -> cancelDownload()
                }
            }
            it.play.setOnClickListener {
                when (data.status) {
                    Status.UNDOWNLOAD -> download()
                    Status.DOWNLOADED -> open()
                    Status.DOWNLOADING -> pauseDownload()
                    Status.PAUSED -> resumeDownload()
                }
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

    private fun getFileSizeFromServer(key: String) {
        CoroutineScope(Dispatchers.IO).launch {
            var fileSize: Int? = 0
            val job = async { fileSize = URL(url).getFileSize() }
            job.await()
            withContext(Dispatchers.Main) {
                fileSize?.let {
                    prefs.save(key, it)
                    val width: Float = ((it.toFloat() / 1024) / 1024)
                    val text = "%.2f Mb".format(width)
                    binding.size.text = "$text"
                }
            }
        }
    }

    private fun download() {
        Log.d("TAG", "download: ${data.title}")
        downloadId = PRDownloader.download(
            url, "${fileManager.APP_FILE_DIRECTORY_PATH}video/",
            fileManager.convertUrlToStoragePath(url, true)
        ).build().setOnStartOrResumeListener {
            binding.play.setImageResource(R.drawable.ic_pause)
            data.status = Status.DOWNLOADING
            Log.d("TAG", "download: setOnStartOrResumeListener")
        }
            .setOnCancelListener {
                binding.pb.visibility = View.INVISIBLE
                binding.play.setImageResource(R.drawable.ic_play)
                data.status = Status.UNDOWNLOAD
                binding.ivDownload.setImageResource(R.drawable.ic_down_arrow)
                val fileSize = prefs.get("file${data.title}", 0)
                val width: Float = ((fileSize.toFloat() / 1024) / 1024)
                val text = "%.2f Mb".format(width)
                binding.size.text = text
            }
            .setOnPauseListener {
                data.status = Status.PAUSED
                binding.play.setImageResource(R.drawable.ic_play)
            }
            .setOnProgressListener {
                it?.let {
                    binding.pb.progress = (it.currentBytes / it.totalBytes).toInt()
                    val current: Float = ((it.currentBytes / 1024) / 1024F)
                    val total: Float = ((it.totalBytes / 1024) / 1024F)
                    binding.size.text = "%.2f".format(current) + "/" + "%.2f".format(total) + " Mb"
                }
            }
            .start(object : OnDownloadListener {
                override fun onDownloadComplete() {
                    binding.pb.visibility = View.INVISIBLE
                    binding.llDownload.visibility = View.GONE
                    binding.play.setImageResource(R.drawable.ic_play)
                    data.status = Status.DOWNLOADED
                    videoLength()
                }

                override fun onError(error: Error?) {
                    binding.pb.visibility = View.INVISIBLE
                    data.status = Status.UNDOWNLOAD
                    binding.play.setImageResource(R.drawable.ic_play)
                    binding.ivDownload.setImageResource(R.drawable.ic_down_arrow)
                }
            })


        Log.d("TAG", "download: $downloadId")
        // downloadRequest.onProgressListener = this
        binding.pb.visibility = View.VISIBLE
        data.status = Status.DOWNLOADING
        binding.ivDownload.setImageResource(R.drawable.ic_baseline_close_24)
        binding.play.setImageResource(R.drawable.ic_pause)
        //downloadRequest.start(this)

    }

    private fun cancelDownload() {
        PRDownloader.cancel(downloadId)
        Log.d("TAG", "cancelDownload: $downloadId")
    }

    private fun pauseDownload() {
        PRDownloader.pause(downloadId)
    }

    private fun resumeDownload() {
        PRDownloader.resume(downloadId)
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