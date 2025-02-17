package hr.tvz.filmhaven.core.state

import hr.tvz.filmhaven.domainobject.FeaturedItemContent
import hr.tvz.filmhaven.domainobject.FeaturedMovie
import hr.tvz.filmhaven.domainobject.Genre
import hr.tvz.filmhaven.domainobject.Movie
import hr.tvz.filmhaven.domainobject.Show

data class HomeMovieState(
    val movies:List<Movie> = emptyList(),
    val tvShows:List<Show> = emptyList(),
    val movieOfTheWeek:Movie = Movie(),
    val genres:List<Genre> = emptyList(),
    val isSearchActive:Boolean= false,
    val searchResults:List<FeaturedMovie> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = ""
)
