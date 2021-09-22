package com.codingwithmitch.dotainfo.ui.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import com.codingwithmitch.dotainfo.R
import com.codingwithmitch.dotainfo.ui.navigation.Screen
import com.codingwithmitch.dotainfo.ui.theme.DotaInfoTheme
import com.example.core.domain.DataState
import com.example.core.domain.ProgressBarState
import com.example.core.domain.UIComponent
import com.example.core.util.Logger
import com.example.hero_domain.exhaustive
import com.example.hero_interactors.HeroInteractors
import com.example.herodetail.HeroDetail
import com.example.herodetail.ui.HeroDetailViewModel
import com.example.herolist.HeroList
import com.example.herolist.ui.HeroListState
import com.example.herolist.ui.HeroListViewModel
import com.squareup.sqldelight.android.AndroidSqliteDriver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = Screen.HeroListScreen.route,
                builder = {
                    addHeroScreen(navController, imageLoader)
                    addHeroDetailScreen(imageLoader = imageLoader)
                }
            )


        }
    }
}

fun NavGraphBuilder.addHeroScreen(
    navController: NavController,
    imageLoader: ImageLoader
) {
    composable(
        route = Screen.HeroListScreen.route
    ) {
        DotaInfoTheme {
            val heroListViewModel: HeroListViewModel = hiltViewModel()
            HeroList(
                state = heroListViewModel.state.value,
                imageLoader = imageLoader,
                onHeroItemSelected = {
                    navController.navigate("${Screen.HeroDetailScreen.route}/$it")
                }
            )
        }
    }
}

fun NavGraphBuilder.addHeroDetailScreen(
    imageLoader: ImageLoader
) {
    composable(
        route = Screen.HeroDetailScreen.route + "/{heroId}",
        arguments = Screen.HeroDetailScreen.arguments
    ) {
        val viewModel: HeroDetailViewModel = hiltViewModel()
        HeroDetail(state = viewModel.state.value, imageLoader = imageLoader)
    }
}













