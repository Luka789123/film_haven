package hr.tvz.filmhaven.navigation

import hr.tvz.filmhaven.core.ContentType
import kotlinx.serialization.Serializable

@Serializable
data class DetailsScreenNavigationData(
    val resourceIdentifier:Int,
    val contentType: ContentType
)
