package com.example.moviesappcompose.destination

import com.example.moviesappcompose.R

sealed class Destination(val route: String, val title: String? = null, val icon: Int? = null)

object SplashScreen : Destination(route = "splash", title = null)

object HomeScreen : Destination(route = "home", title = "Home", icon = R.drawable.home_icon)

object SearchScreen : Destination(route = "search", title = "Search", icon = R.drawable.search_icon)

object BrowseScreen : Destination(route = "browse", title = "Browse", icon = R.drawable.brows_icon)

object WatchListScreen : Destination(route = "watchList", title = "Watch List", icon = R.drawable.watchlist_icon)


