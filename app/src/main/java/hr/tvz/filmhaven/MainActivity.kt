package hr.tvz.filmhaven

import hr.tvz.filmhaven.ui.route.PaginatedRoute
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dagger.hilt.android.AndroidEntryPoint
import hr.tvz.filmhaven.domainobject.Genre
import hr.tvz.filmhaven.navigation.DetailsScreenNavigationData
import hr.tvz.filmhaven.navigation.HomeScreenNavigationData
import hr.tvz.filmhaven.navigation.PaginationScreenNavigationData
import hr.tvz.filmhaven.ui.route.DetailsRoute
import hr.tvz.filmhaven.ui.route.HomeRoute
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
                    composable<HomeScreenNavigationData> {
                        HomeRoute(navController = navController)
                    }
                    composable<PaginationScreenNavigationData> {
                        val args = it.toRoute<PaginationScreenNavigationData>();
                        PaginatedRoute(title = args.title, genre = Genre(id = args.genreId, name = args.genreName), contentType = args.contentType, navController = navController)
                    }
                    composable<DetailsScreenNavigationData> {
                        val args = it.toRoute<DetailsScreenNavigationData>();
                        DetailsRoute(id=args.resourceIdentifier, contentType = args.contentType)
                    }
                }
            }
        }
    }
}

