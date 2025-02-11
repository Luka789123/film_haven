package hr.tvz.filmhaven.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier

@Composable
fun DetailsScreen(resourceIdentifier:Int){
    LaunchedEffect(Unit) {
        println(resourceIdentifier);
    }
    Column (
        modifier = Modifier.fillMaxSize()
    ) {
        Text("Hello from details Screen")
    }
}