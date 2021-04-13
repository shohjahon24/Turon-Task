package uz.hashteam.turontask.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.EventLog
import android.util.Log
import android.view.View
import android.widget.PopupWindow
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.koin.android.ext.android.inject
import uz.hashteam.turontask.MainActivity
import uz.hashteam.turontask.R
import uz.hashteam.turontask.data.video.Status
import uz.hashteam.turontask.data.video.VideoX
import uz.hashteam.turontask.databinding.FragmentMainBinding
import uz.hashteam.turontask.list.main.adapter.MainAdapter
import uz.hashteam.turontask.list.main.callback.VideoCallBack
import uz.hashteam.turontask.util.FileManager

class MainFragment : Fragment(R.layout.fragment_main), VideoCallBack {

    private val adapter: MainAdapter by inject()
    private val fileManager: FileManager by inject()

    lateinit var binding: FragmentMainBinding

    private var popupMenu: PopupWindow? = null

    private var data: ArrayList<VideoX> = ArrayList()
    private var edData: ArrayList<VideoX> = ArrayList()
    private var ingData: ArrayList<VideoX> = ArrayList()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
        adapter.callBack = this
        binding.listVideo.adapter = adapter
        binding.listVideo.recycledViewPool.setMaxRecycledViews(0, 10)
        binding.listVideo.setItemViewCacheSize(10)
        (activity as MainActivity).let {
            data.clear()
            data.addAll(it.getVideos())
            adapter.setData(data)
        }
        /*binding.filter.setOnClickListener {
            showMenu()
        }*/
    }

    override fun onItemClick(data: VideoX) {
        findNavController().navigate(
            R.id.action_pageFragment_to_playFragment,
            bundleOf(
                "url" to data.sources[0],
                "description" to data.description,
                "title" to data.title
            )
        )
    }

    override fun onStatusChanged(data: VideoX) {
        EventBus.getDefault().post(data)
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun test(eventLog: EventLog) {

    }

    // @SuppressLint("ClickableViewAccessibility")
/*
    private fun showMenu() {
        val inflater =
            activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
                ?: LayoutInflater.from(context)
        val menuBinder: MenuVideoBinding =
            MenuVideoBinding.inflate(inflater, null, false)
        popupMenu = PopupWindow(context)
        popupMenu?.setBackgroundDrawable(null)
        popupMenu?.isFocusable = true
        popupMenu?.contentView = menuBinder.root
        popupMenu?.showAsDropDown(binding.filter)
        menuBinder.all.setOnClickListener {
            allList()
            popupMenu?.dismiss()
        }
        menuBinder.downloaded.setOnClickListener {
            downloadedList()
            popupMenu?.dismiss()
        }
        menuBinder.downloading.setOnClickListener {
            downloadingList()
            popupMenu?.dismiss()
        }

    }
*/

    /*  private fun downloadedList() {
          edData.clear()
          data.forEach {
              if (fileManager.hasOfflineFile(it.sources[0], "video", true)) {
                  edData.add(it)
              }
          }
          adapter.type = 2
          Log.d("TAG", "downloadedList: ${edData.size}")
          adapter.setData(edData)
      }

      private fun downloadingList() {
          adapter.type = 1
          adapter.setData(ingData)
      }

      private fun allList() {
          adapter.type = 0
          adapter.setData(data)
      }*/
}