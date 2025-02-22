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
    val title: String = "",
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
    @Transient
    val cast:List<Person>? = emptyList(),
    @SerializedName("genre_ids")
     val genreIds:List<Long> = emptyList()

):FeaturedItemContent,FeaturedMovie,DetailsContract{
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

    override fun provideTitle(): String {
        return title
    }

    override fun provideGenreString(): String {
        val stringBuilder = StringBuilder()
        for (genre in genres){
            stringBuilder.append(genre.name)
            stringBuilder.append(" ")
        }
        return stringBuilder.toString().trim().replace(" ",",")
    }

    override fun provideRating(): String {
        return getRating()
    }

    override fun provideOverview(): String {
        return  overview
    }

    override fun provideCast(): List<Person> {
        return  cast?: emptyList()
    }

    override fun provideBackdropPath(): String {
        return "${Constants.POSTER_IMAGE_URL}$backdropPath"
    }

    override fun providePoster(): String {
        return  getImagePath()
    }
}

