package uz.hashteam.turontask.data.video

data class Video(
    val videos: List<VideoX>
)

data class VideoX(
    val description: String,
    val sources: List<String>,
    val subtitle: String,
    val thumb: String,
    val title: String
)