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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import hr.tvz.filmhaven.domainobject.FeaturedMovie

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieTile(item: FeaturedMovie){
    Box (
        modifier = Modifier.padding(5.dp)
    ) {
        Row {
            GlideImage(
                model = item.getImagePath(),
                contentDescription = item.getItemTitle(),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp, 81.dp)
                    .clip(RoundedCornerShape(5.dp))
            )
            Column (
                modifier = Modifier.padding(start = 10.dp)
            ) {
                Text(text = item.getItemTitle(), style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Filled.Star, contentDescription = "rating Icon", modifier = Modifier.size(width = 15.dp, height = 15.dp))
                    Text(item.getRating(), modifier = Modifier.padding(start = 10.dp), style = MaterialTheme.typography.labelLarge)
                }
                Text(text = item.getGenre(),style = MaterialTheme.typography.labelLarge, overflow = TextOverflow.Ellipsis)
            }
        }
    }
}
