package uz.hashteam.turontask.ui.play

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentViewHolder
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import uz.hashteam.turontask.R
import uz.hashteam.turontask.databinding.FragmentMainBinding
import uz.hashteam.turontask.databinding.FragmentPlayBinding

class PlayFragment : Fragment(R.layout.fragment_play) {

    lateinit var player: SimpleExoPlayer

    var url = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentPlayBinding.bind(view)
        player = ExoPlayerFactory.newSimpleInstance(context)
        binding.back.setOnClickListener { findNavController().popBackStack() }
        binding.playerView.player = player
        arguments?.let {
            url = it["url"].toString()
            binding.description.text = it["description"].toString()
            binding.title.text = it["title"].toString()
        }
        val dataSource = DefaultDataSourceFactory(context, Util.getUserAgent(context, "Avtostart"))
        val mediaSource = ExtractorMediaSource.Factory(dataSource)
            .setExtractorsFactory(DefaultExtractorsFactory()).createMediaSource(Uri.parse(url))
        player.prepare(mediaSource, true, false)
        player.playWhenReady = true
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }
}