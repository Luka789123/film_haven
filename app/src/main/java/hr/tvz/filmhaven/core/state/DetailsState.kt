package hr.tvz.filmhaven.core.state

import hr.tvz.filmhaven.domainobject.DetailsContract
import hr.tvz.filmhaven.domainobject.Genre

data class DetailsState(
    val content:DetailsContract? = null,
    val loadedCategories:List<Genre> = emptyList(),
    val isLoading:Boolean = true
)
