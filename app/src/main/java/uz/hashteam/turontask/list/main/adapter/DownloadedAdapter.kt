package uz.hashteam.turontask.list.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.hashteam.turontask.data.video.Status
import uz.hashteam.turontask.data.video.VideoX
import uz.hashteam.turontask.databinding.ItemVideoBinding
import uz.hashteam.turontask.list.main.callback.VideoCallBack
import uz.hashteam.turontask.list.main.viewHolder.DownloadedHolder
import uz.hashteam.turontask.list.main.viewHolder.ViewHolder
import uz.hashteam.turontask.util.FileManager
import uz.hashteam.turontask.util.Prefs

class DownloadedAdapter(private val fileManager: FileManager) :
    RecyclerView.Adapter<DownloadedHolder>() {

    private var data: ArrayList<VideoX> = ArrayList()

    var callBack: VideoCallBack? = null

    fun changeData(videoX: VideoX) {
        when (videoX.status) {
            Status.DOWNLOADED -> {
                if (data.indexOf(videoX) == -1) {
                    data.add(videoX)
                    notifyItemInserted(data.size - 1)
                }
            }
            Status.PAUSED -> {
                if (data.indexOf(videoX) == -1) {
                    data.remove(videoX)
                    notifyDataSetChanged()
                }
            }
            Status.UNDOWNLOAD -> {
                data.remove(videoX)
                notifyDataSetChanged()
            }
            Status.DOWNLOADING -> {
                data.remove(videoX)
                notifyDataSetChanged()
            }
        }
    }

    fun setData(data: ArrayList<VideoX>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DownloadedHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemVideoBinding.inflate(layoutInflater, parent, false)
        return DownloadedHolder(binding, fileManager)
    }

    override fun onBindViewHolder(holder: DownloadedHolder, position: Int) {
        holder.bind(data[position])
        holder.listener = {
            callBack?.onItemClick(it)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}