package hr.tvz.filmhaven.ui.route

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hr.tvz.filmhaven.ui.screen.DiscoverScreen
import hr.tvz.filmhaven.ui.screen.HomeScreen
import hr.tvz.filmhaven.ui.widget.NavBarIcon

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
                0 -> HomeScreen(
                    navController = navController,
                    paddingValues = innerPadding
                )
                1 -> DiscoverScreen(paddingValues = innerPadding)
                2 -> Text("Settings")
            }
        }
    }
}

