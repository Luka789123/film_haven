package hr.tvz.filmhaven.ui.route


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import hr.tvz.filmhaven.core.ContentType
import hr.tvz.filmhaven.core.SortingKey
import hr.tvz.filmhaven.core.SortingProperties
import hr.tvz.filmhaven.domainobject.Genre
import hr.tvz.filmhaven.domainobject.Movie
import hr.tvz.filmhaven.domainobject.Show
import hr.tvz.filmhaven.navigation.DetailsScreenNavigationData
import hr.tvz.filmhaven.ui.widget.MovieTile
import hr.tvz.filmhaven.viewmodel.PaginatedViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaginatedRoute(title: String, genre: Genre,contentType: ContentType,viewModel: PaginatedViewModel = hiltViewModel(),navController: NavController) {
    val state by viewModel.state.collectAsState()
    val listState = rememberLazyListState()
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    Scaffold(
        topBar = { TopAppBar(
            title = { Text(text = title) },
            actions = {
                IconButton(onClick =  {
                    scope.launch {
                        if (sheetState.isVisible) {
                            sheetState.hide()
                        } else {
                            sheetState.show()
                        }
                    }.invokeOnCompletion {
                        showBottomSheet = sheetState.isVisible
                    }
                }) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.Sort, contentDescription = "Sort content")
                }
            }
            ) },


    ) { innerPadding ->
       if (state.isInitialLoading){
           Box(
               modifier = Modifier
                   .fillMaxSize()
                   .padding(innerPadding),
               contentAlignment = Alignment.Center


           ){
               CircularProgressIndicator()
           }
       } else if (state.errorString.isNotBlank()){
           Box(
               modifier = Modifier
                   .fillMaxSize()
                   .padding(innerPadding),
               contentAlignment = Alignment.Center


           ){
               Text(text = state.errorString)
           }
       }
        else {
            if (showBottomSheet){
                ModalDrawerSheet(
                    modifier = Modifier.padding(innerPadding)
                ) {
                    Column {
                        ListTile(sortingKey = SortingProperties.TITLE,
                            isSelected = state.sortKey.code == SortingProperties.TITLE.code,
                            isAsc = state.sortKey.order ==  "asc"
                        ) { key ->  viewModel.setSortKey(key)}
                        ListTile(sortingKey = SortingProperties.REVENUE,
                            isSelected = state.sortKey.code == SortingProperties.REVENUE.code,
                            isAsc = state.sortKey.order ==  "asc"
                        ) { key ->  viewModel.setSortKey(key) }
                        ListTile(sortingKey = SortingProperties.POPULARITY,
                            isSelected = state.sortKey.code == SortingProperties.POPULARITY.code,
                            isAsc = state.sortKey.order ==  "asc"
                        ) { key ->  viewModel.setSortKey(key) }
                        ListTile(sortingKey = SortingProperties.VOTE_COUNT,
                            isSelected = state.sortKey.code == SortingProperties.VOTE_COUNT.code,
                            isAsc = state.sortKey.order ==  "asc"
                        ) { key ->  viewModel.setSortKey(key) }
                        ListTile(sortingKey = SortingProperties.VOTE_AVERAGE,
                            isSelected = state.sortKey.code == SortingProperties.VOTE_AVERAGE.code,
                            isAsc = state.sortKey.order ==  "asc"
                        ) { key ->  viewModel.setSortKey(key) }
                        ListTile(sortingKey = SortingProperties.ORIGINAL_TITLE,
                            isSelected = state.sortKey.code == SortingProperties.ORIGINAL_TITLE.code,
                            isAsc = state.sortKey.order ==  "asc"
                        ) { key ->  viewModel.setSortKey(key) }
                        ListTile(sortingKey = SortingProperties.PRIMARY_RELEASE_DATE,
                            isSelected = state.sortKey.code == SortingProperties.PRIMARY_RELEASE_DATE.code,
                            isAsc = state.sortKey.order ==  "asc"
                        ) { key ->  viewModel.setSortKey(key) }
                    }
                }

            }


         else  {
               LazyColumn(
                   state = listState
               ) {

                   items(state.loadedContent.size) { i ->
                       run {
                           if (state.loadedContent.size - 1 == i && state.isPageLoading) {
                               Box(
                                   modifier = Modifier
                                       .fillMaxWidth()
                                       .padding(horizontal = 20.dp)
                                       .height(50.dp),
                                   contentAlignment = Alignment.Center
                               ) {
                                   CircularProgressIndicator()
                               }
                           } else {
                               MovieTile(state.loadedContent[i]){
                                   item -> navController.navigate(DetailsScreenNavigationData(
                                   resourceIdentifier = if(item is Movie) item.id.toInt() else (item as Show).id.toInt(),contentType=contentType ))
                               }
                           }

                       }
                   }
               }
           }
        }
    }
    LaunchedEffect (Unit) {
        viewModel.loadInitialContent(contentType= contentType, genre = genre)
    }
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleItemIndex ->
                if (lastVisibleItemIndex == state.loadedContent.size - 1 && !state.isPageLoading && !state.isInitialLoading) {
                   viewModel.loadNextPage()
                }
            }
    }
}


@Composable
fun ListTile(sortingKey:SortingKey,isSelected:Boolean,isAsc:Boolean,onClick:(key:SortingKey)->Unit){
    Box (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .clickable { onClick(sortingKey) }

    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = sortingKey.name, modifier = Modifier.padding( vertical = 10.dp))
            if(isSelected) Icon(imageVector = if (isAsc)  Icons.Filled.ArrowUpward else Icons.Filled.ArrowDownward, contentDescription = "Icon")
        }
    }
}