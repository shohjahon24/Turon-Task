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
import uz.hashteam.turontask.databinding.ItemDownloadingBinding
import uz.hashteam.turontask.databinding.ItemVideoBinding
import uz.hashteam.turontask.util.FileManager
import uz.hashteam.turontask.util.Prefs
import uz.hashteam.turontask.util.getFileSize
import java.net.URL
import java.util.concurrent.TimeUnit

class DownloadingHolder(
    private val binding: ItemDownloadingBinding,
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(video: VideoX) {
        binding.title.text = video.title
    }
}