package uz.hashteam.turontask.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import org.koin.android.ext.android.inject
import uz.hashteam.turontask.MainActivity
import uz.hashteam.turontask.R
import uz.hashteam.turontask.databinding.FragmentMainBinding
import uz.hashteam.turontask.list.main.adapter.MainAdapter

class MainFragment : Fragment(R.layout.fragment_main) {

    private val adapter: MainAdapter by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMainBinding.bind(view)
        binding.listVideo.adapter = adapter
        binding.listVideo.recycledViewPool.setMaxRecycledViews(0, 10)
        binding.listVideo.setItemViewCacheSize(10)
        (activity as MainActivity).let {
            adapter.setData(it.getVideos().videos)
        }
    }
}