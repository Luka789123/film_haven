package hr.tvz.filmhaven.ui.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import hr.tvz.filmhaven.domainobject.FeaturedMovie


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun FeaturedMovieWidget(featuredMovie: FeaturedMovie){
    Column (modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)) {
        Text("Movie of the day", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(vertical = 10.dp))
        Row {
            GlideImage(
                modifier = Modifier.size(120.dp,200.dp).clip(RoundedCornerShape(10.dp)),
                model = featuredMovie.getImagePath(),
                contentScale = ContentScale.Crop,
                contentDescription = featuredMovie.getItemTitle())
            Column (
                modifier = Modifier.padding(top = 12.dp, start = 10.dp)
            ) {
                Text(text = featuredMovie.getItemTitle(), style = MaterialTheme.typography.titleMedium)
                Text(text = featuredMovie.getDescription(),
                  style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 5)
                Row {
                    Icon(imageVector = Icons.Filled.Star, contentDescription = "")
                    Text(text = featuredMovie.getRating(), style = MaterialTheme.typography.bodyMedium,)
                }
            }
        }
    }



}