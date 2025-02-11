package hr.tvz.filmhaven.domainobject

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
class MoviePage(
    @SerializedName("results")
    val items: List<Movie>,
    val page: Int,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResult: Long,
)