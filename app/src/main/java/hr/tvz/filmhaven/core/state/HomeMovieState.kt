package hr.tvz.filmhaven.core.state

import hr.tvz.filmhaven.domainobject.Movie
import hr.tvz.filmhaven.domainobject.Show

data class HomeMovieState(
    val movies:List<Movie> = emptyList(),
    val tvShows:List<Show> = emptyList(),
    val movieOfTheWeek:Movie = Movie(),
    val isLoading: Boolean = false,
    val error: String? = ""
)
