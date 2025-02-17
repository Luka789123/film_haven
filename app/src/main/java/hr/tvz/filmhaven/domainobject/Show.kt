package hr.tvz.filmhaven.domainobject

import com.google.gson.annotations.SerializedName
import hr.tvz.filmhaven.core.Constants
import org.intellij.lang.annotations.Language
import java.util.Locale

data class Show(
     @SerializedName("backdrop_path")
    val backdropPath:String,
    val id:Long,
    val name:String,
    @SerializedName("original_name")
    val originalName:String,
    val overview:String,
    @SerializedName("poster_path")
    val posterPath:String,
    @SerializedName("media_type")
    val mediaType:String,
    val adult:Boolean,
    @SerializedName("original_language")
    val originalLanguage: String,
    val popularity: Double,
    @SerializedName("first_air_date")
    val firstAirDate: String,
     @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int,
     @SerializedName("genre_ids")
     val genreIds:List<Long>,
     @Transient
     val genres:List<Genre>
) : FeaturedItemContent,FeaturedMovie{
     override fun getDescription(): String {
        return  overview
     }

     override fun getRating(): String {
         return String.format(Locale.GERMAN,"%.1f", voteAverage)
     }

     override fun getItemTitle(): String {
         return name
     }

     override fun getImagePath(): String {
         return "${Constants.IMAGE_BASE_URL}$posterPath"
     }

     override fun getGenre(): String {
         var genreString:List<String> = emptyList();
         if (genres == null){
             return  "No genres"
         }
         if ( genres.size > 2){
             return "${genres.first().name} | ${genres.last().name}"
         }
         val builder:StringBuilder = StringBuilder();

         for (element in genres){
             builder.append(element.name);
             builder.append(" ")
         }
         return builder.toString().trim().replace(" "," | ")
     }

}
