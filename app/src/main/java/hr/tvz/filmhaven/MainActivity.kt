package hr.tvz.filmhaven

import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.House
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dagger.hilt.android.AndroidEntryPoint
import hr.tvz.filmhaven.navigation.DetailsScreenNavigationData
import hr.tvz.filmhaven.navigation.HomeScreenNavigationData
import hr.tvz.filmhaven.ui.screen.DetailsScreen
import hr.tvz.filmhaven.ui.screen.HomeRoute
import hr.tvz.filmhaven.ui.screen.HomeScreen
import hr.tvz.filmhaven.ui.theme.AppTheme

import hr.tvz.filmhaven.viewmodel.HomeViewModel
import kotlinx.serialization.Serializable
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme  {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = HomeScreenNavigationData::class){
                    composable<DetailsScreenNavigationData>{
                        val args = it.toRoute<DetailsScreenNavigationData>();
                        DetailsScreen(resourceIdentifier = args.resourceIdentifier)
                    }
                    composable<HomeScreenNavigationData> {
                        HomeRoute(navController = navController)
                    }
                }
            }
        }
    }
}

