package hr.tvz.filmhaven.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import hr.tvz.filmhaven.core.ContentType
import hr.tvz.filmhaven.domainobject.FeaturedItemContent
import hr.tvz.filmhaven.domainobject.FeaturedMovie
import hr.tvz.filmhaven.domainobject.Genre
import hr.tvz.filmhaven.domainobject.Movie
import hr.tvz.filmhaven.domainobject.Pagination
import hr.tvz.filmhaven.domainobject.Show
import hr.tvz.filmhaven.navigation.DetailsScreenNavigationData
import hr.tvz.filmhaven.navigation.PaginationScreenNavigationData
import hr.tvz.filmhaven.ui.widget.FeaturedContentSlider
import hr.tvz.filmhaven.ui.widget.MovieTile
import hr.tvz.filmhaven.viewmodel.DiscoverViewModel


@Composable
fun DiscoverScreen(viewModel: DiscoverViewModel = hiltViewModel(),navController: NavController, paddingValues: PaddingValues) {
    val selectedIndex = remember { mutableStateOf(0) }
    val state by viewModel.state.collectAsState()
    Scaffold(
        modifier = Modifier.padding(paddingValues),
        topBar = {
            TabRow(selectedTabIndex = selectedIndex.value) {

                TextButton(onClick = { selectedIndex.value = 0 }) {
                    Text(text = "Movies")
                }
                TextButton(onClick = { selectedIndex.value = 1 }) {
                    Text(text = "TV Show")
                }

            }
        }
    ) { innerPadding ->
        if (!state.error.isNullOrBlank()) {
            Text(state.error?: "", modifier = Modifier.padding(innerPadding))
        } else {

               when(selectedIndex.value){
                   0 ->  MovieDiscoverTab(
                       paddingValues = innerPadding,
                       selectedCategories = state.selectedMovieCategories,
                       genres = state.movieCategories,
                       initialValues = state.initialMovieData,
                       content = state.foundMovies,
                       navController = navController,
                       contentType = ContentType.MOVIE,
                       onCategoryClick = { genre: Genre -> viewModel.addMovieCategoryToResultSet(genre) }
                   ) { genre: Genre -> viewModel.removeMovieCategoryFromResultSet(genre) }
                   1->  MovieDiscoverTab(
                       paddingValues = innerPadding,
                       selectedCategories = state.selectedShowCategories,
                       genres = state.tvShowCategory,
                       initialValues = state.initialShowData,
                       content = state.foundTvShows,
                       navController = navController,
                      contentType = ContentType.TV_SHOW,
                       onCategoryClick = { genre: Genre -> viewModel.addShowCategoryToResultSet(genre) }
                   ) { genre: Genre -> viewModel.removeShowCategoryFromResultSet(genre) }
               }

        }
    }
}

@Composable
fun MovieDiscoverTab(
    paddingValues: PaddingValues,
    genres: List<Genre>,
    selectedCategories: List<Genre>?,
    contentType: ContentType,
    navController: NavController,
    initialValues: List<FeaturedMovie>,
    content: Map<Genre, List<FeaturedItemContent>>,
    onCategoryClick: (genre: Genre) -> Unit,
    onCategoryCancel:(genre:Genre) -> Unit
) {

    Column(
        modifier = Modifier.padding(
          horizontal = 10.dp
        )
    ) {

        LazyHorizontalStaggeredGrid(
            modifier = Modifier.height(150.dp).padding(bottom = 10.dp),
            rows = StaggeredGridCells.Fixed(3)
        ) {
            items(genres.size) { item ->
                GenreFilterTile(
                    item = genres[item],
                    isSelected = selectedCategories?.contains(genres[item]) ?: false,
                    onClick = { onCategoryClick(genres[item]) }
                ) {onCategoryCancel(genres[item])}
            }
        }
        if (initialValues.isNotEmpty()) {
            LazyColumn {
                items(initialValues.size) { i -> MovieTile(initialValues[i]) {
                    item -> navController.navigate(DetailsScreenNavigationData(resourceIdentifier = if (item is Movie) item.id.toInt() else (item as Show).id.toInt(),
                    contentType = contentType ))
                } }
            }
        } else {
            LazyColumn {
                items(content.size) { index: Int ->
                    FeaturedContentSlider(
                        featuredItems = content[content.keys.toList()[index]] ?: emptyList(),
                        blockTitle = content.keys.toList()[index].name,
                        onTileClicked = {
                                item -> navController.navigate(DetailsScreenNavigationData(resourceIdentifier = if (item is Movie) item.id.toInt() else (item as Show).id.toInt(),
                            contentType = contentType ))
                        }
                    ) {
                        navController.navigate(PaginationScreenNavigationData(
                            title = content.keys.toList()[index].name,
                            genreId = content.keys.toList()[index].id,
                            genreName = content.keys.toList()[index].name,
                            contentType = contentType))
                    }
                }
            }
        }
    }
}

@Composable
fun GenreFilterTile(item: Genre, isSelected: Boolean, onClick: (genre: Genre) -> Unit,onCancel:(genre:Genre) -> Unit) {
    Box(
        modifier = Modifier.padding(top = 10.dp, end = 10.dp)
    ) {
        TextButton(
            onClick = { if (!isSelected)  onClick(item) else onCancel(item) },
            modifier = Modifier
                .border(
                    1.dp,
                    MaterialTheme.colorScheme.primary,
                    RoundedCornerShape(28.dp)
                )
                .clip(RoundedCornerShape(28.dp))
                .height(50.dp)
                .background(if (!isSelected) Color.Transparent else MaterialTheme.colorScheme.primary)
        ) {
            Text(text = item.name, style = TextStyle(color = if (!isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary))
        }
    }
}