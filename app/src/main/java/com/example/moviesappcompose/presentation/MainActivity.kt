package com.example.moviesappcompose.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.moviesappcompose.destination.BrowseScreen
import com.example.moviesappcompose.destination.HomeScreen
import com.example.moviesappcompose.destination.SearchScreen
import com.example.moviesappcompose.destination.SplashScreen
import com.example.moviesappcompose.destination.WatchListScreen
import com.example.moviesappcompose.presentation.browse.CategorizedMoviesScreen
import com.example.moviesappcompose.presentation.home.HomeScreen
import com.example.moviesappcompose.presentation.home.HomeScreenViewModel
import com.example.moviesappcompose.presentation.movieDetails.MovieDetailsScreen
import com.example.moviesappcompose.presentation.splash.SplashScreen
import com.example.moviesappcompose.ui.theme.MoviesAppComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            val bottomNavExcludeRoutes = listOf(
                SplashScreen.route,
                "details/{movie_id}",
                "browse_result/{genre_id}"
            )
            MoviesAppComposeTheme {
                Scaffold(
                    bottomBar = {
                        if (bottomNavExcludeRoutes.none { currentRoute?.startsWith(it) == true })
                            BottomNavigationBar(navController)
                    }
                ) {
                    MoviesApp(navController)
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val screens = listOf(
        HomeScreen,
        SearchScreen,
        BrowseScreen,
        WatchListScreen
    )
    NavigationBar(
        containerColor = Color(0xFF1A1A1A)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        screens.forEach { screen ->
            NavigationBarItem(
                selected = currentRoute == screen.route,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFFFFBB3B),
                    indicatorColor = Color.Transparent,
                    unselectedIconColor = Color(0xFFC6C6C6),
                    selectedTextColor = Color(0xFFFFBB3B),
                    unselectedTextColor = Color(0xFFC6C6C6)
                ),
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = screen.icon!!),
                        contentDescription = null,
                        modifier = Modifier.size(25.dp)
                    )
                },
                label = {
                    Text(text = screen.title!!)
                }
            )
        }
    }
}

@Composable
fun MoviesApp(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen() {
                navController.navigate("home") {
                    popUpTo("splash") {
                        inclusive = true
                    }
                }
            }
        }
        composable("home") {
            val viewModel: HomeScreenViewModel = hiltViewModel()
            val topState = viewModel.topMoviesState
            val latestState = viewModel.latestMovieState
            val recommendedState = viewModel.recommendedMoviesState
            HomeScreen(topState, latestState, recommendedState, {
                viewModel.checkIsFavorite(it)
            }, {
                viewModel.toggleFavoriteMovie(it)
            }) {
                navController.navigate("details/$it")
            }
        }
        composable("search") {
            com.example.moviesappcompose.presentation.search.SearchScreen()
        }
        composable("details/{movie_id}", arguments = listOf(navArgument("movie_id") {
            type = NavType.IntType
        })) {
            val movieId = it.arguments?.getInt("movie_id")
//            val viewmodel: MovieDetailsScreenViewModel = hiltViewModel()
//            val state = viewmodel.movieDetailsState
            MovieDetailsScreen(movieId!!)

        }
        composable(BrowseScreen.route) {
            com.example.moviesappcompose.presentation.browse.BrowseScreen{
                navController.navigate("browse_result/$it")
            }
        }
        composable("browse_result/{genre_id}", arguments = listOf(navArgument("genre_id") {
            type = NavType.IntType
        })) {
            val genreId = it.arguments?.getInt("genre_id")
            CategorizedMoviesScreen(genreId = genreId!!)
        }
    }
}