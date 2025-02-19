package hr.tvz.filmhaven.navigation

import hr.tvz.filmhaven.core.ContentType
import hr.tvz.filmhaven.domainobject.Genre
import kotlinx.serialization.Serializable

@Serializable
data class PaginationScreenNavigationData(
   val title: String,
   val genreId:Long,
   val genreName:String,
   val contentType: ContentType
)