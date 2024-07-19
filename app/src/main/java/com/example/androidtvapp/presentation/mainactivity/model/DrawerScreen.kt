package com.example.androidtvapp.presentation.mainactivity.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.androidtvapp.R

sealed class DrawerScreen(
    val route: String,
    val icon: ImageVector,
    @StringRes val  title: Int,
    @StringRes val descriptionId: Int,
) {

    data object UserAccount: DrawerScreen(
        "UserAccount",
        Icons.Outlined.AccountCircle,
        R.string.user_account,
        R.string.user_account,
    )
    data object SearchScreen: DrawerScreen(
        "SearchScreen",
        Icons.Outlined.Search,
        R.string.search,
        R.string.search,
    )

    data object HomeScreen : DrawerScreen(
        "HomeScreen",
        Icons.Outlined.Home,
        R.string.home,
        R.string.home,
    )

    data object SettingsScreen : DrawerScreen(
        "SettingsScreen",
        Icons.Outlined.Settings,
        R.string.settings,
        R.string.settings
    )
}

val screenList = listOf(
    DrawerScreen.SearchScreen,
    DrawerScreen.HomeScreen,
)