package hr.tvz.filmhaven.ui.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import hr.tvz.filmhaven.domainobject.FeaturedItemContent


@Composable
fun FeaturedContentSlider(featuredItems:List<FeaturedItemContent>,blockTitle:String,onShowMoreCallback:()->Unit) {

   Column (
       modifier = Modifier.padding(vertical = 16.dp)
   ) {
       Text(text = blockTitle, modifier = Modifier.padding(bottom = 5.dp), style = MaterialTheme.typography.titleLarge)
       LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(featuredItems.size) { index -> HomeTile(featuredItems.get(index)) }
        }
    }

}