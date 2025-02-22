package hr.tvz.filmhaven.ui.widget

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import hr.tvz.filmhaven.core.ContentType
import hr.tvz.filmhaven.domainobject.FeaturedMovie
import hr.tvz.filmhaven.domainobject.Movie
import hr.tvz.filmhaven.domainobject.Show
import hr.tvz.filmhaven.navigation.DetailsScreenNavigationData

@Composable
fun PageableList(items:List<FeaturedMovie>,navController: NavController){
    LazyColumn {
        items(items.size ) {index: Int -> MovieTile(items[index]){
            items -> navController.navigate(DetailsScreenNavigationData(
                resourceIdentifier = if (items is Movie) items.id.toInt() else (items as Show).id.toInt(),
                contentType = if (items is Movie) ContentType.MOVIE else ContentType.TV_SHOW
            ))
        } }
    }
}