package hr.tvz.filmhaven.core.state

import hr.tvz.filmhaven.core.ContentType
import hr.tvz.filmhaven.core.SortingProperties
import hr.tvz.filmhaven.core.SortingKey
import hr.tvz.filmhaven.domainobject.FeaturedMovie
import hr.tvz.filmhaven.domainobject.Genre

data class PaginatedState(
    val loadedContent:List<FeaturedMovie> = emptyList(),
    val loadedGenres:List<Genre> = emptyList(),
    val currentGenre: Genre? = null,
    val totalPages:Int = 1,
    val sortKey:SortingKey = SortingProperties.TITLE,
    val currentContent:ContentType = ContentType.MOVIE,
    val isInitialLoading:Boolean = false,
    val isPageLoading:Boolean = false,
    val errorString:String =""
)
