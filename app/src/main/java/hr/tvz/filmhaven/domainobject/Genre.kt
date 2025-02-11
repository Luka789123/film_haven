package hr.tvz.filmhaven.domainobject

import kotlinx.serialization.Serializable


@Serializable
data class Genre(val id:Long,val name:String)
