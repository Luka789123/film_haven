package hr.tvz.filmhaven.core

object SortingProperties {
    val ORIGINAL_TITLE: SortingKey = SortingKey(code = "original_title", order = "asc", name = "Original title")
    val POPULARITY: SortingKey =  SortingKey(code = "popularity", order = "asc", name = "Popularity")
    val REVENUE: SortingKey =  SortingKey(code = "revenue", order = "asc", name = "Revenue")
    val TITLE: SortingKey =  SortingKey(code = "title", order = "asc", name = "Title")
    val PRIMARY_RELEASE_DATE: SortingKey =  SortingKey(code = "primary_release_date", order = "asc", name = "Primary release date")
    val VOTE_AVERAGE: SortingKey =  SortingKey(code = "vote_average", order = "asc", name = "Vote average")
    val VOTE_COUNT: SortingKey =  SortingKey(code = "vote_count", order = "asc", name = "Vote count")
}

 data class SortingKey(
    val name:String,
    val order:String,
    val code:String
)