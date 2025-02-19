package hr.tvz.filmhaven.domainobject

import com.google.gson.annotations.SerializedName
import hr.tvz.filmhaven.core.Constants
import kotlinx.serialization.Serializable

@Serializable
data class Person(
    val adult: Boolean,
    val gender: Int,
    val id: Int,
    val name:String,
    val character:String,
    @SerializedName("original_name")
    val originalName: String,
    val popularity: Double,
    @SerializedName("profile_path")
    val profilePath: String,
) : FeaturedItemContent{
    override fun getItemTitle(): String {
       return name;
    }

    override fun getImagePath(): String {
        return "${Constants.IMAGE_BASE_URL}${profilePath}"
    }

    override fun getGenre(): String {
        return character
    }
}
