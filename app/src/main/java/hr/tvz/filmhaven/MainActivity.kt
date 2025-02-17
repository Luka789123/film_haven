package hr.tvz.filmhaven

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dagger.hilt.android.AndroidEntryPoint
import hr.tvz.filmhaven.navigation.DetailsScreenNavigationData
import hr.tvz.filmhaven.navigation.HomeScreenNavigationData
import hr.tvz.filmhaven.ui.route.HomeRoute
import hr.tvz.filmhaven.ui.screen.DetailsScreen
import hr.tvz.filmhaven.ui.theme.AppTheme


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

