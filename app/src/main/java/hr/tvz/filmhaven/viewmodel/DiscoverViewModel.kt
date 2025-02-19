package hr.tvz.filmhaven.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hr.tvz.filmhaven.core.GenreMapper
import hr.tvz.filmhaven.core.state.DiscoverMovieState
import hr.tvz.filmhaven.domainobject.FeaturedMovie
import hr.tvz.filmhaven.domainobject.Genre
import hr.tvz.filmhaven.domainobject.Movie
import hr.tvz.filmhaven.domainobject.Pagination
import hr.tvz.filmhaven.domainobject.Show
import hr.tvz.filmhaven.repository.DiscoverMovieRepository
import hr.tvz.filmhaven.repository.HomeMovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(private val repository: DiscoverMovieRepository, private val homeRepository: HomeMovieRepository, val savedStateHandle: SavedStateHandle) :ViewModel(){

    private val _state = MutableStateFlow(DiscoverMovieState())
    val state: StateFlow<DiscoverMovieState> get()  = _state
    private var _page = 1;
    init {
    setUpInitialData()
    }

    fun addMovieCategoryToResultSet(genre:Genre){
       viewModelScope.launch {
           _state.value = _state.value.copy(isLoading = true)
           if (_state.value.initialMovieData.isNotEmpty())
           {
               _state.value = _state.value.copy(initialMovieData = emptyList())
           }
           val  newValueList = _state.value.selectedMovieCategories.toMutableList();
           newValueList.add(genre)
           val movieGenreList = _state.value.foundMovies.toMutableMap()
           val filteredList = GenreMapper.mapGenresToMovies(movies = repository.getFilteredMovieContentByCategory("${genre.id}").items, genres = _state.value.movieCategories)
           movieGenreList[genre] = filteredList
           if (movieGenreList.size <= 1){
               _state.value = _state.value.copy(
                   selectedMovieCategories = newValueList,
                   foundMovies = movieGenreList,
                   initialMovieData = movieGenreList[movieGenreList.keys.first()]?: emptyList(),
                   error = null,
                   isLoading = false)

           }
         else{
               _state.value = _state.value.copy(
                   selectedMovieCategories = newValueList,
                   foundMovies = movieGenreList,
                   error = null,
                   isLoading = false)
         }
       }
    }


    fun addShowCategoryToResultSet(genre:Genre){
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            if (_state.value.initialShowData.isNotEmpty())
            {
                _state.value = _state.value.copy(initialShowData = emptyList())
            }
            val  newValueList = _state.value.selectedShowCategories.toMutableList();
            newValueList.add(genre)
            val showGenreList = _state.value.foundTvShows.toMutableMap()
            val filteredList = GenreMapper.mapGenresToShows(shows = repository.getFilteredShowContentByCategory("${genre.id}").items, genres = _state.value.tvShowCategory)
            showGenreList[genre] = filteredList
            if (showGenreList.size <= 1){
                _state.value = _state.value.copy(
                    selectedShowCategories = newValueList,
                    foundTvShows = showGenreList,
                    initialShowData = showGenreList[showGenreList.keys.first()]?: emptyList(),
                    error = null,
                    isLoading = false)

            }
            else{
                _state.value = _state.value.copy(
                    selectedShowCategories = newValueList,
                    foundTvShows = showGenreList,
                    error = null,
                    isLoading = false)
            }
        }
    }




    fun removeMovieCategoryFromResultSet(category:Genre){
        val resultMap = _state.value.foundMovies.toMutableMap();
        resultMap.remove(category);
        val selectedCategories = _state.value.selectedMovieCategories.toMutableList()
        selectedCategories.remove(category)

        _state.value = _state.value.copy(foundMovies = resultMap, selectedMovieCategories = selectedCategories)
        if (_state.value.foundMovies.size == 1){
            _state.value = _state.value.copy(foundMovies = resultMap, selectedMovieCategories = selectedCategories, initialMovieData = resultMap[resultMap.keys.toList().first()]?: emptyList())
            return
        }
        else if(_state.value.foundMovies.isEmpty()){
            viewModelScope.launch {
               _state.value = _state.value.copy(initialMovieData = GenreMapper.mapGenresToMovies(repository.getFilteredMovieContent().items,_state.value.movieCategories), isLoading = false)
            }
        }

    }

    fun removeShowCategoryFromResultSet(category:Genre){
        val resultMap = _state.value.foundTvShows.toMutableMap();
        resultMap.remove(category);
        val selectedCategories = _state.value.selectedShowCategories.toMutableList()
        selectedCategories.remove(category)

        _state.value = _state.value.copy(foundTvShows = resultMap, selectedShowCategories = selectedCategories)
        if (_state.value.foundTvShows.size == 1){
            _state.value = _state.value.copy(foundTvShows = resultMap, selectedShowCategories = selectedCategories, initialShowData = resultMap[resultMap.keys.toList().first()]?: emptyList())
            return
        }
        else if(_state.value.foundTvShows.isEmpty()){
            viewModelScope.launch {
                _state.value = _state.value.copy(initialShowData = GenreMapper.mapGenresToShows(repository.getFilteredShowContent().items,_state.value.tvShowCategory), isLoading = false)
            }
        }

    }

    private fun stringifyCategoryIds(categories: List<Genre>): String {
        return if (categories.size == 1) {
            val stringBuilder = StringBuilder()
            for (category in categories) {
                stringBuilder.append(category.id)
                stringBuilder.append(" ")
            }
            stringBuilder.toString().trim().replace(" ","|")

        } else {
            "${categories.first().id}"
        }
    }

    private fun setUpInitialData(){
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                _state.value = _state
                    .value
                    .copy(
                        movieCategories = repository.getMovieCategories().genres,
                        tvShowCategory = repository.getTvShowCategories().genres,
                        isLoading = false
                    )
                _state.value = _state.value.copy(
                    initialMovieData = GenreMapper.mapGenresToMovies(repository.getFilteredMovieContent().items,_state.value.movieCategories),
                    initialShowData = GenreMapper.mapGenresToShows(repository.getFilteredShowContent().items,_state.value.tvShowCategory),
                )

            } catch (e:Exception){
                _state.value = _state.value.copy(error = e.message)
            }
        }
    }



}