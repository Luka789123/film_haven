package hr.tvz.filmhaven.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hr.tvz.filmhaven.core.ContentType
import hr.tvz.filmhaven.core.GenreMapper
import hr.tvz.filmhaven.core.state.DetailsState
import hr.tvz.filmhaven.core.state.DiscoverMovieState
import hr.tvz.filmhaven.domainobject.Movie
import hr.tvz.filmhaven.domainobject.MoviePage
import hr.tvz.filmhaven.domainobject.Show
import hr.tvz.filmhaven.repository.DetailsRepository
import hr.tvz.filmhaven.repository.DiscoverMovieRepository
import hr.tvz.filmhaven.repository.HomeMovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository: DetailsRepository,private val discoverRepository: DiscoverMovieRepository, val savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _state = MutableStateFlow(DetailsState())
    val state: StateFlow<DetailsState> get()  = _state

    fun test(){
        println("Hello world!")
    }

    fun loadContent(id:Int,contentType: ContentType){
        viewModelScope.launch {
            try {
                val content = if(contentType== ContentType.MOVIE) repository.getMovieDetails(id) else repository.getShowDetails(id)
                val cast = if(contentType== ContentType.MOVIE) repository.getMovieCast(id) else repository.getShowCast(id)
                if (content is Movie){
                    _state.value = _state.value.copy(
                        isLoading = false,
                        loadedCategories = emptyList(),
                        content = content.copy(cast = cast.cast)
                    )
                }
                else if (content is Show){
                    _state.value = _state.value.copy(
                        isLoading = false,
                        loadedCategories = emptyList(),
                        content = content.copy(cast = cast.cast, mediaType = "", genreIds = emptyList(), genres = emptyList())
                    )
                }
            } catch (e: Exception) {
                println(e.message)
            }
        }
    }
}