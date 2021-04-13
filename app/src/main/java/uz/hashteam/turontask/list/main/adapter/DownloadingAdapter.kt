package uz.hashteam.turontask.list.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.hashteam.turontask.data.video.Status
import uz.hashteam.turontask.data.video.VideoX
import uz.hashteam.turontask.databinding.ItemDownloadingBinding
import uz.hashteam.turontask.databinding.ItemVideoBinding
import uz.hashteam.turontask.list.main.callback.VideoCallBack
import uz.hashteam.turontask.list.main.viewHolder.DownloadedHolder
import uz.hashteam.turontask.list.main.viewHolder.DownloadingHolder
import uz.hashteam.turontask.list.main.viewHolder.ViewHolder
import uz.hashteam.turontask.util.FileManager
import uz.hashteam.turontask.util.Prefs

class DownloadingAdapter() :
    RecyclerView.Adapter<DownloadingHolder>() {

    private var data: ArrayList<VideoX> = ArrayList()

    fun changeData(videoX: VideoX) {
        when (videoX.status) {
            Status.DOWNLOADING -> {
                if (data.indexOf(videoX) == -1) {
                    data.add(videoX)
                    notifyItemInserted(data.size - 1)
                }
            }
            Status.PAUSED -> {
                if (data.indexOf(videoX) == -1) {
                    data.add(videoX)
                    notifyItemInserted(data.size - 1)
                }
            }
            Status.UNDOWNLOAD -> {
                data.remove(videoX)
                notifyDataSetChanged()
            }
            Status.DOWNLOADED -> {
                data.remove(videoX)
                notifyDataSetChanged()
            }
        }
    }

    fun setData(data: ArrayList<VideoX>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DownloadingHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemDownloadingBinding.inflate(layoutInflater, parent, false)
        return DownloadingHolder(binding)
    }

    override fun onBindViewHolder(holder: DownloadingHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }
}