package hr.tvz.filmhaven.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hr.tvz.filmhaven.core.state.HomeMovieState
import hr.tvz.filmhaven.domainobject.Genre
import hr.tvz.filmhaven.domainobject.Movie
import hr.tvz.filmhaven.domainobject.MoviePage
import hr.tvz.filmhaven.domainobject.Show
import hr.tvz.filmhaven.repository.HomeMovieRepository
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel
@Inject constructor(val repository:HomeMovieRepository,val savedStateHandle: SavedStateHandle)
    : ViewModel(){

        private val _state = mutableStateOf(HomeMovieState())
        val state: State<HomeMovieState> = _state
        init {
           getData()

        }

        private fun getData(){
            viewModelScope.launch {

                try {
                    _state.value = _state.value.copy(isLoading = true)
                    var movies:List<Movie> =   repository.getTrendingMovies().items
                    var shows:List<Show> = repository.getTrendingTvShows().items
                    val genres:List<Genre> = repository.getMovieCategories().genres
                    val categories:Map<Long,Genre> =
                        genres.associateBy { it.id }
                   movies = movies.map { e ->  e.copy(genres = e.genreIds.mapNotNull { id -> categories[id] }) }
                 shows =   shows.map { e ->  e.copy(genres = e.genreIds.mapNotNull { id -> categories[id] }) }
                    _state.value = _state.value.copy(tvShows = shows,movies = movies, isLoading = false, movieOfTheWeek = getMovieOfTheWeek(movies), genres = genres)
                }catch (
                    e:Exception
                ){
                    _state.value = HomeMovieState(isLoading = false, error = "An error occurred")
                }
            }
        }

    private fun getMovieOfTheWeek(movies:List<Movie>):Movie{
        var movieOfTheWeek = movies.first()
        for (movie:Movie in movies){
            if (movie.voteAverage > movieOfTheWeek.voteAverage ){
                movieOfTheWeek = movie;
            }
        }
            return movieOfTheWeek
        }

    fun searchMovies(query:String){
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, isSearchActive = query.length > 2)
            try {

                if (query.length > 2){
                    var movies:List<Movie> =   repository.searchForMovie(query).items
                    val categories:Map<Long,Genre> =
                        _state.value.genres.associateBy { it.id }
                    movies = movies.map { e ->  e.copy( genres = e.genreIds.mapNotNull { id -> categories[id] },
                        backdropPath = e.backdropPath ?: "",
                        posterPath = e.posterPath ?: "",
                        title = e.title ?: "",
                        overview = e.overview ?: "") }
                    _state.value = _state.value.copy( searchResults = movies)
                }
                _state.value = _state.value.copy(isLoading = false)

        } catch (e:Exception){
            _state.value = _state.value.copy(error = e.message)
        } }
    }


    }