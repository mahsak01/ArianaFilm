package com.mgpersia.androidbox.Fragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.mgpersia.androidbox.databinding.FragmentVideoPlayBinding

class PlayVideoFragment : Fragment() {

    private lateinit var binding: FragmentVideoPlayBinding


    override fun onResume() {
        super.onResume()
        setListener()
        setInformation()
    }

    private fun setInformation() {

        val url = "https://lms.shahroodut.ac.ir/Upload/Resource/R_e258fb00b83a44639bd906a791c4ea7a.mp4"
        val exoPlayer = ExoPlayer.Builder(requireContext()).build()
        val mediaItem = MediaItem.fromUri(Uri.parse(url))
        exoPlayer.addMediaItem(mediaItem)

        this.binding.idExoPlayerVIew.player = exoPlayer


        exoPlayer.prepare()
        exoPlayer.playWhenReady = true
    }

    private fun setListener() {
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVideoPlayBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}