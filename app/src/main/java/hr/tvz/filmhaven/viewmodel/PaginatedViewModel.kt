package hr.tvz.filmhaven.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hr.tvz.filmhaven.core.ContentType
import hr.tvz.filmhaven.core.SortingKey
import hr.tvz.filmhaven.core.SortingProperties
import hr.tvz.filmhaven.core.state.DiscoverMovieState
import hr.tvz.filmhaven.core.state.PaginatedState
import hr.tvz.filmhaven.domainobject.FeaturedMovie
import hr.tvz.filmhaven.domainobject.Genre
import hr.tvz.filmhaven.domainobject.Movie
import hr.tvz.filmhaven.domainobject.MoviePage
import hr.tvz.filmhaven.domainobject.Show
import hr.tvz.filmhaven.domainobject.ShowPage
import hr.tvz.filmhaven.repository.DiscoverMovieRepository
import hr.tvz.filmhaven.repository.HomeMovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaginatedViewModel@Inject constructor(private val repository: DiscoverMovieRepository,val savedStateHandle: SavedStateHandle) : ViewModel() {
    private val _state = MutableStateFlow(PaginatedState())
    val state: StateFlow<PaginatedState> get()  = _state
    private var _page = 1;


    fun loadInitialContent(contentType: ContentType,genre: Genre){
        _page = 1
        viewModelScope.launch {
            _state.value = PaginatedState(
                isInitialLoading = true,
                currentGenre = genre,
                errorString = "",
                currentContent = contentType)
            try {
                val initialGenreList = fetchInitialGenreList(contentType)
                val initialContent =  fetchInitialContent(contentType=contentType,genre=genre, genreList = initialGenreList)
                _state.value = _state.value.copy(
                    isInitialLoading = false,
                    loadedContent = initialContent.content,
                    totalPages = initialContent.totalPages,
                    loadedGenres = initialGenreList)
            } catch (e:Exception){
                _state.value = _state.value.copy(errorString = e.message?: "An error occurred")
            }
        }
    }

    fun loadNextPage(){
        if (_state.value.totalPages == 0 || _state.value.totalPages == _page){
            return
        }
        viewModelScope.launch {
            _state.value = _state.value.copy(isPageLoading = true, errorString = "")
            _page++;
            try {
                when(_state.value.currentContent){
                    ContentType.MOVIE -> loadNextMoviePage()
                    ContentType.TV_SHOW -> loadNextShowPage()
                }
            } catch (e:Exception){
                _state.value = _state.value.copy(isPageLoading = false, errorString = e.message?: "An error had happened while trying to load content")
            }
        }
    }


    private suspend fun loadNextMoviePage(){
        val initialContent = _state.value.loadedContent.toMutableList()
        val currentGenre:Genre = _state.value.currentGenre!!
        val nextPageContent =  repository.getFilteredMovieContentByCategory(
            genres = currentGenre.id.toString(),
            page = _page,
            sortKey = createSortingKeyString(_state.value.sortKey))
        initialContent.addAll(mapGenresToMovies(nextPageContent.items,_state.value.loadedGenres))
        _state.value = _state.value.copy(loadedContent = initialContent, isPageLoading = false)
    }

    private suspend fun loadNextShowPage(){
        val initialContent = _state.value.loadedContent.toMutableList()
        val currentGenre:Genre = _state.value.currentGenre!!
        val nextPageContent =  repository.getFilteredShowContentByCategory(
            genres = currentGenre.id.toString(),
            page = _page,
            sortKey = createSortingKeyString(_state.value.sortKey))
        initialContent.addAll(mapGenresToShows(nextPageContent.items,_state.value.loadedGenres))
        _state.value = _state.value.copy(loadedContent = initialContent, isPageLoading = false)
    }

    fun setSortKey(sortKey:SortingKey){
        viewModelScope.launch {
            _page = 0
            var currentStateKey = _state.value.sortKey
            if (sortKey.code != currentStateKey.code){
                _state.value = _state.value.copy(sortKey = sortKey, loadedContent = emptyList())
            }
            else{
                _state.value = _state.value.copy(sortKey= currentStateKey.copy(order = if (currentStateKey.order == "asc") "desc" else "asc"), loadedContent = emptyList())
            }

            loadNextPage()
        }
    }

    fun createSortingKeyString(key: SortingKey):String{
        return "${key.code}.${key.order}"
    }

    private suspend fun fetchInitialGenreList(contentType: ContentType):List<Genre>{
       return if(contentType == ContentType.MOVIE) repository.getMovieCategories().genres
       else repository.getTvShowCategories().genres
    }

    private suspend fun fetchInitialContent(contentType: ContentType,genre: Genre,genreList: List<Genre>):InitialContentWrapper{
         if(contentType == ContentType.MOVIE) {
           val moviePage: MoviePage = repository.getFilteredMovieContentByCategory(genres = genre.id.toString())
           val totalPages:Int = moviePage.totalPages
           val resultSet:List<Movie> =  mapGenresToMovies(moviePage.items,genreList)
             return InitialContentWrapper(totalPages=totalPages, content = resultSet)
        }

             val showPage: ShowPage = repository.getFilteredShowContentByCategory(genres = genre.id.toString())
             val totalPages:Int = showPage.totalPages
             val resultSet:List<Show> =  mapGenresToShows(showPage.items,genreList)
             return InitialContentWrapper(totalPages=totalPages, content = resultSet)

    }

    private fun mapGenresToMovies(movies: List<Movie>, genres: List<Genre>): List<Movie> {
        return movies.mapNotNull { movie ->
            movie.backdropPath?.let {
                movie.copy(genres = movie.genreIds.mapNotNull { id -> genres.find { it.id == id } })
            }
        }
    }

    private fun mapGenresToShows(shows: List<Show>, genres: List<Genre>): List<Show> {
        return shows.mapNotNull { show ->
            show.copy(genres = show.genreIds.mapNotNull { id -> genres.find { it.id == id } }, mediaType = show.mediaType?: "", backdropPath = show.backdropPath?:"", posterPath = show.posterPath?:"")
        }
    }


    data class InitialContentWrapper(val totalPages:Int = 0,val content:List<FeaturedMovie> )

}
