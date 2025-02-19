package hr.tvz.filmhaven.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import hr.tvz.filmhaven.core.ContentType
import hr.tvz.filmhaven.domainobject.Movie
import hr.tvz.filmhaven.domainobject.Show
import hr.tvz.filmhaven.navigation.DetailsScreenNavigationData
import hr.tvz.filmhaven.ui.widget.FeaturedContentSlider
import hr.tvz.filmhaven.ui.widget.FeaturedMovieWidget
import hr.tvz.filmhaven.ui.widget.PageableList
import hr.tvz.filmhaven.ui.widget.SearchField
import hr.tvz.filmhaven.viewmodel.HomeViewModel


@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel(), navController: NavController, paddingValues: PaddingValues){
    val state = viewModel.state.value

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
                SearchField { value -> viewModel.searchMovies(value)}

            if (state.isLoading){
                Box (
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator()
                }
            }
               if (state.isSearchActive){
                   PageableList(items=state.searchResults,navController=navController)
               }
               if (!state.error.isNullOrBlank()){
                   Text(text = state.error)
               }
               else {
                  Column (
                      modifier = Modifier.verticalScroll(rememberScrollState()).padding(horizontal = 10.dp)
                  ) {
                      FeaturedMovieWidget(state.movieOfTheWeek)
                      FeaturedContentSlider(featuredItems = state.movies,
                          blockTitle = "Trending Movies",
                          onTileClicked = {item -> navController
                              .navigate(DetailsScreenNavigationData(
                                  contentType = ContentType.MOVIE,
                                    resourceIdentifier = (item as Movie).id.toInt()
                              ))}) {  }
                      FeaturedContentSlider(
                          featuredItems = state.tvShows,
                          blockTitle = "Trending Shows",
                          onTileClicked = {item -> navController
                              .navigate(DetailsScreenNavigationData(
                                  contentType = ContentType.TV_SHOW,
                                  resourceIdentifier = (item as Show).id.toInt()
                              ))}
                          ) { }
                  }
               }

    }
}














