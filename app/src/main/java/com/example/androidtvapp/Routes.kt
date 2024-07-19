package com.example.androidtvapp

sealed interface Routes {
    val route: String

    data object Home: Routes{
        override val route: String = "home"
    }
}