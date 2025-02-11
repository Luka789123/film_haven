package hr.tvz.filmhaven.domainobject

import com.google.gson.annotations.SerializedName
import hr.tvz.filmhaven.core.Constants
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import java.util.Locale
import kotlin.jvm.Throws

@Serializable
 data class Movie(
    @SerializedName("backdrop_path")
    val backdropPath: String = "",
    val id: Long = 0L,
    private val title: String = "",
    @SerializedName("original_title")
    val originalTitle: String = "",
    val overview: String = "",
    @SerializedName("poster_path")
    val posterPath: String = "",
    @SerializedName("media_type")
    val mediaType: String = "",
    val adult: Boolean = false,
    @SerializedName("original_language")
    val originalLanguage: String = "",
    val popularity: Double = 0.0,
    @SerializedName("release_date")
    val releaseDate: String = "",
    val video: Boolean = false,
    @SerializedName("vote_average")
    val voteAverage: Double = 0.0,
    @SerializedName("vote_count")
    val voteCount: Double = 0.0,
    @Transient
    val genres: List<Genre> = emptyList(),
    @SerializedName("genre_ids")
     val genreIds:List<Long> = emptyList()

):FeaturedItemContent,FeaturedMovie{
    override fun getDescription(): String {
       return overview
    }

    override fun getRating(): String {
        return String.format(Locale.GERMAN,"%.1f", voteAverage)
    }

    override fun getItemTitle(): String {
        return title;
    }

    override fun getGenre(): String {
        var genreString:List<String> = emptyList();
        if (genres.size > 2){
            return "${genres.first().name} | ${genres.last().name}"
        }
        val builder:StringBuilder = StringBuilder();

        for (element in genres){
            builder.append(element.name);
            builder.append(" ")
        }
        return builder.toString().trim().replace(" "," | ")
    }

    override fun getImagePath(): String {
        return "${Constants.IMAGE_BASE_URL}$posterPath"
    }
}

