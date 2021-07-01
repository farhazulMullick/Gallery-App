
import com.google.gson.annotations.SerializedName

data class FlickrData(
    @SerializedName("photos")
    val photos: Photos,
    @SerializedName("stat")
    val stat: String
)