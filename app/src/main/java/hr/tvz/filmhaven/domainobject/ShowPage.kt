package hr.tvz.filmhaven.domainobject

import com.google.gson.annotations.SerializedName

data class ShowPage(
    @SerializedName("results")
    val items: List<Show>,
    val page: Int,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResult: Long,
)
