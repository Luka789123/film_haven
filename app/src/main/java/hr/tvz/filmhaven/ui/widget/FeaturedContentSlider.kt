package hr.tvz.filmhaven.ui.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hr.tvz.filmhaven.domainobject.FeaturedItemContent
import hr.tvz.filmhaven.domainobject.FeaturedMovie


@Composable
fun FeaturedContentSlider(featuredItems:List<FeaturedItemContent>,blockTitle:String,onTileClicked:(FeaturedItemContent) -> Unit,onShowMoreCallback:()->Unit) {

   Column (
       modifier = Modifier.padding(vertical = 16.dp)
   ) {
       Row (
           modifier = Modifier.fillMaxWidth(),
           verticalAlignment = Alignment.CenterVertically,
           horizontalArrangement = Arrangement.SpaceBetween
       ) {
           Text(text = blockTitle, modifier = Modifier.padding(bottom = 5.dp), style = MaterialTheme.typography.labelLarge)
           TextButton(onClick = onShowMoreCallback) {
               Row(
                   verticalAlignment = Alignment.CenterVertically
               ) {
                   Text(text = "Show more", modifier = Modifier.padding(end = 10.dp), style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.onSurface))
                   Icon(
                       imageVector = Icons.Filled.ChevronRight,
                       tint = MaterialTheme.colorScheme.onSurface,
                       contentDescription = "Show more",
                       modifier = Modifier.size(20.dp))
               }
           }
       }
       LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(featuredItems.size) { index -> HomeTile(featuredItems[index]) { item -> onTileClicked(item)} }
        }
    }

}