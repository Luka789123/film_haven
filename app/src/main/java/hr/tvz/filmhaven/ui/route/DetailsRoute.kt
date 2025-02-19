package hr.tvz.filmhaven.ui.route

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import hr.tvz.filmhaven.core.ContentType
import hr.tvz.filmhaven.ui.widget.FeaturedContentSlider
import hr.tvz.filmhaven.viewmodel.DetailsViewModel

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DetailsRoute(id:Int,contentType: ContentType,viewModel: DetailsViewModel = hiltViewModel()){
   val state by viewModel.state.collectAsState()

   LaunchedEffect (Unit) {
        viewModel.loadContent(id=id,contentType= contentType)
    }

    Scaffold {
        innerPadding -> run {
        if (state.isLoading){
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.padding(innerPadding).fillMaxSize()){
                CircularProgressIndicator()
            }
        } else {
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
                Box (
                    contentAlignment = Alignment.TopCenter,
                    modifier = Modifier.fillMaxWidth().fillMaxHeight(0.45f)
                ) {
                    GlideImage(
                        model = state.content!!.provideBackdropPath(),
                        modifier = Modifier.fillMaxWidth(),
                        contentDescription = "Backdrop poster")
                    Column (
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.height(350.dp).width(200.dp)) {
                        GlideImage(
                            model = state.content!!.providePoster(),
                            modifier = Modifier.size(width = 80.dp, height = 124.dp).clip(
                                RoundedCornerShape(20.dp)
                            ),
                            contentDescription = "Backdrop poster")
                        Text(
                            text = state.content!!.provideTitle(),
                            style = MaterialTheme.typography.titleLarge.copy(textAlign = TextAlign.Center), maxLines = 2,
                            overflow = TextOverflow.Ellipsis)
                        Text(text = state.content!!.provideGenreString())
                        Row (modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(imageVector = Icons.Filled.Star, contentDescription = "", modifier = Modifier.padding(end = 10.dp))
                            Text(text = state.content!!.provideRating()) }
                    }

        }
             Column (modifier = Modifier.padding(horizontal = 10.dp)) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.fillMaxWidth()){
                    Text("Overview", modifier = Modifier.padding(bottom = 10.dp), style = MaterialTheme.typography.labelLarge)
                    Box(modifier =
                    Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(color = MaterialTheme.colorScheme.primaryContainer)
                        .padding(10.dp)
                    ){
                        Text(state.content!!.provideOverview(), modifier = Modifier.padding(10.dp),
                            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onErrorContainer))
                    }
                }

                 FeaturedContentSlider(featuredItems = state.content!!.provideCast(), blockTitle = "Cast", onTileClicked = {}) { }
             }
            }




    }
        }
    }
}

