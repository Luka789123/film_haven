package hr.tvz.filmhaven.ui.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import hr.tvz.filmhaven.domainobject.FeaturedItemContent

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun HomeTile(item:FeaturedItemContent){
    Column (
        modifier = Modifier.size(width = 107.dp, height = 180.dp).padding(end = 10.dp),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GlideImage(
            model = item.getImagePath(),
            contentDescription = item.getItemTitle(),
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(100.dp,142.dp).clip(RoundedCornerShape(10.dp))
        )
        Text(text = item.getItemTitle(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold))
        Text(
            text = item.getGenre(),
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight(500)))

    }
}