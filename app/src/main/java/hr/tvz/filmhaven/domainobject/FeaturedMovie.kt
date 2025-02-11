package hr.tvz.filmhaven.domainobject

interface FeaturedMovie:FeaturedItemContent {
    fun getDescription():String
    fun getRating():String
}