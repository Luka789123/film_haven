package hr.tvz.filmhaven.core.state

import hr.tvz.filmhaven.domainobject.Genre
import hr.tvz.filmhaven.domainobject.Movie
import hr.tvz.filmhaven.domainobject.Show

data class DiscoverMovieState(
    val selectedMovieCategories:List<Genre> = emptyList(),
    val selectedShowCategories:List<Genre> = emptyList(),
    val foundMovies:Map<Genre,List<Movie>> = emptyMap(),
    val foundTvShows:Map<Genre,List<Show>> = emptyMap(),
    val isLoading:Boolean = false,
    val movieCategories:List<Genre> = emptyList(),
    val initialMovieData:List<Movie> = emptyList(),
    val initialShowData:List<Show> = emptyList(),
    val tvShowCategory:List<Genre> = emptyList(),
    val error:String? = ""
)

enum class DiscoverMovieCategory(){
    MOVIE,
    TV_SHOW
}