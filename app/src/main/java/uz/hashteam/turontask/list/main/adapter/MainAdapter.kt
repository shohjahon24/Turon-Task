package uz.hashteam.turontask.list.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.hashteam.turontask.data.video.VideoX
import uz.hashteam.turontask.databinding.ItemVideoBinding
import uz.hashteam.turontask.list.main.callback.VideoCallBack
import uz.hashteam.turontask.list.main.viewHolder.ViewHolder
import uz.hashteam.turontask.util.FileManager
import uz.hashteam.turontask.util.Prefs

class MainAdapter(private val prefs: Prefs, private val fileManager: FileManager) :
    RecyclerView.Adapter<ViewHolder>() {

    private var data: List<VideoX> = ArrayList()

    var callBack: VideoCallBack? = null

    fun setData(data: ArrayList<VideoX>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemVideoBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(prefs, binding, fileManager)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
        holder.listener = {
            callBack?.onItemClick(it)
        }
        holder.statusListener = { v ->
            callBack?.onStatusChanged(v)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}