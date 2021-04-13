package uz.hashteam.turontask.list.main.callback

import uz.hashteam.turontask.data.video.VideoX

interface VideoCallBack {
    fun onItemClick(data: VideoX)
    fun onStatusChanged(data: VideoX)
}