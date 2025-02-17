package hr.tvz.filmhaven.ui.widget

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import hr.tvz.filmhaven.domainobject.FeaturedMovie

@Composable
fun PageableList(items:List<FeaturedMovie>){
    LazyColumn {
        items(items.size ) {index: Int -> MovieTile(items[index])}
    }
}