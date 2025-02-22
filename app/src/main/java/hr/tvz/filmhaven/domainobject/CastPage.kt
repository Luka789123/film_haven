package hr.tvz.filmhaven.domainobject

import kotlinx.serialization.Serializable

@Serializable
data class CastPage(
    val cast:List<Person>,
)
