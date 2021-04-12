package uz.hashteam.turontask.data.video

data class Video(
    val videos: List<VideoX>
)

data class VideoX(
    val description: String,
    var sources: List<String>,
    val subtitle: String,
    val thumb: String,
    val title: String,
    var status: Int
)

class Status {
    companion object {
        val UNDOWNLOAD = 0
        val DOWNLOADING = 1
        val DOWNLOADED = 2
        val PAUSED = 3
    }
}