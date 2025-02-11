package hr.tvz.filmhaven.ui.screen

import android.media.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import hr.tvz.filmhaven.ui.widget.FeaturedContentSlider
import hr.tvz.filmhaven.ui.widget.FeaturedMovieWidget
import hr.tvz.filmhaven.viewmodel.HomeViewModel





@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel(),navController: NavController){
    val state = viewModel.state.value
    if (state.isLoading){
        CircularProgressIndicator()
    }
    else if (state.error?.isNotBlank() == true){
        Text(text = state.error)
    }
    Column (
        modifier = Modifier.fillMaxSize().padding(20.dp)
    ) {
        SearchField { value ->  }
               FeaturedMovieWidget(state.movieOfTheWeek)
               FeaturedContentSlider(featuredItems = state.movies, blockTitle = "Trending Movies") { }
               FeaturedContentSlider(featuredItems = state.tvShows, blockTitle = "Trending Shows") { }

    }
}

@Composable
fun MovieItem(name: String) {
    Text(text = name, modifier = Modifier.padding(16.dp))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeRoute(navController: NavController) {
    val pageIndex = remember { mutableStateOf(0) }
    Scaffold(

        bottomBar = {
            BottomAppBar (

            ) {
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    NavBarIcon(
                        icon = Icons.Filled.Home,
                        label = "Home",
                        isSelected = pageIndex.value == 0) { pageIndex.value = 0 }

                    NavBarIcon(
                        icon = Icons.Filled.Explore,
                        label = "Discover",
                        isSelected = pageIndex.value == 1) { pageIndex.value = 1 }
                    NavBarIcon(
                        icon = Icons.Filled.Settings,
                        label = "Settings",
                        isSelected = pageIndex.value == 2) { pageIndex.value = 2 }
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(top = innerPadding.calculateTopPadding())) {
            when (pageIndex.value) {
                0 -> HomeScreen(navController = navController)
                1 -> Text("Search")
                2 -> Text("Settings")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchField(onChanged:(value:String)->Unit ){

    var text by remember { mutableStateOf(TextFieldValue("")) }

    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
            onChanged(it.text)

        },
        prefix = { Icon(imageVector = Icons.Filled.Search, contentDescription = null) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary
        ),
        shape = RoundedCornerShape(28.dp),
        modifier = Modifier.fillMaxWidth())


}


@Composable
fun  NavBarIcon( icon:ImageVector,label:String, isSelected:Boolean,onClickedCallBack:()-> Unit){
    return Box(
        modifier = if (isSelected) Modifier
            .clip(RoundedCornerShape(35.dp))
            .background( MaterialTheme.colorScheme.primary)
        else
            Modifier.clip(RoundedCornerShape(35.dp))
                .background( Color.Transparent)
    ){
        IconButton(
            onClick = onClickedCallBack,
            Modifier.padding(horizontal = 20.dp, vertical = 2.dp)
            ) {
            Icon(
                modifier = Modifier.size(width = 30.dp, height = 30.dp),
                imageVector = icon,
                tint = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface,
                contentDescription = label)
        }
    }
}

