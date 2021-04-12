package uz.hashteam.turontask.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.android.ext.android.inject
import uz.hashteam.turontask.MainActivity
import uz.hashteam.turontask.R
import uz.hashteam.turontask.data.video.VideoX
import uz.hashteam.turontask.databinding.FragmentMainBinding
import uz.hashteam.turontask.databinding.MenuVideoBinding
import uz.hashteam.turontask.list.main.adapter.MainAdapter
import uz.hashteam.turontask.list.main.callback.VideoCallBack

class MainFragment : Fragment(R.layout.fragment_main), VideoCallBack {

    private val adapter: MainAdapter by inject()

    lateinit var binding: FragmentMainBinding

    private var popupMenu: PopupWindow? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
        adapter.callBack = this
        binding.listVideo.adapter = adapter
        binding.listVideo.recycledViewPool.setMaxRecycledViews(0, 10)
        binding.listVideo.setItemViewCacheSize(10)
        (activity as MainActivity).let {
            adapter.setData(it.getVideos().videos)
        }
        binding.filter.setOnClickListener {
            showMenu()
        }
    }

    override fun onItemClick(data: VideoX) {
        findNavController().navigate(
            R.id.action_mainFragment_to_playFragment,
            bundleOf(
                "url" to data.sources[0],
                "description" to data.description,
                "title" to data.title
            )
        )
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun showMenu() {
        val inflater =
            activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
                ?: LayoutInflater.from(context)
        val menuBinder: MenuVideoBinding =
            MenuVideoBinding.inflate(inflater, null, false)
        popupMenu = PopupWindow(context)
        popupMenu?.setBackgroundDrawable(null)
        popupMenu?.contentView = menuBinder.root
        popupMenu?.showAsDropDown(binding.filter)
        menuBinder.all.setOnClickListener {
            popupMenu?.dismiss()
        }
        menuBinder.downloaded.setOnClickListener {
            popupMenu?.dismiss()
        }
        menuBinder.downloading.setOnClickListener {
            popupMenu?.dismiss()
        }

    }

}