package hr.tvz.filmhaven.domainobject

import kotlinx.serialization.Serializable

@Serializable
data class GenrePage(
    val genres:List<Genre>
)
