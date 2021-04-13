package uz.hashteam.turontask.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.android.ext.android.inject
import uz.hashteam.turontask.R
import uz.hashteam.turontask.data.video.Status
import uz.hashteam.turontask.data.video.VideoX
import uz.hashteam.turontask.databinding.FragmentMainBinding
import uz.hashteam.turontask.list.main.adapter.DownloadingAdapter
import uz.hashteam.turontask.list.main.callback.VideoCallBack

class DownloadingFragment : Fragment(R.layout.fragment_main) {

    private val adapter: DownloadingAdapter by inject()

    lateinit var binding: FragmentMainBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
        binding.listVideo.adapter = adapter
        binding.listVideo.recycledViewPool.setMaxRecycledViews(0, 10)
        binding.listVideo.setItemViewCacheSize(10)
        adapter.setData(ArrayList())
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun updateData(video: VideoX) {
        adapter.changeData(video)
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }
}