package com.example.androidtvapp.presentation.mainactivity

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.tv.material3.DrawerValue
import androidx.tv.material3.Icon
import androidx.tv.material3.NavigationDrawer
import androidx.tv.material3.NavigationDrawerItem
import androidx.tv.material3.Surface
import androidx.tv.material3.Text
import androidx.tv.material3.rememberDrawerState
import com.example.androidtvapp.Routes
import com.example.androidtvapp.presentation.mainactivity.model.MainViewEvent
import com.example.androidtvapp.ui.theme.AndroidTVAppTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidTVAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    shape = RectangleShape
                ) {
                    AppHost()
                }
            }
        }
    }

    @Composable
    fun AppHost() {
        val navController = rememberNavController()
        val drawerState = rememberDrawerState(DrawerValue.Closed)

        NavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                Column(
                    Modifier
                        .fillMaxHeight()
                        .background(color = Color(0xFF3e3e42))
                        .padding(12.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    NavigationDrawerItem(
                        selected = navController.currentDestination?.route == Routes.Home.route,
                        leadingContent = {
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = null
                            )
                        },
                        onClick = {

                        },
                    ) {
                        Text(text = "Home")
                    }
                }


            }
        ) {
            NavHost(navController = navController, startDestination = Routes.Home.route) {
                composable(Routes.Home.route) {
                    MainScreen(mainViewModel = mainViewModel)
                }
            }
        }
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        val action: Int = event.action
        val keyCode: Int = event.keyCode
        when (keyCode) {
            KeyEvent.KEYCODE_DPAD_DOWN -> {
                if (action == KeyEvent.ACTION_DOWN && event.isLongPress) {
                    mainViewModel.handleEvent(MainViewEvent.OnScreenLoad)
                    return true
                }
                return super.dispatchKeyEvent(event)
            }
            else -> return super.dispatchKeyEvent(event)
        }
    }
}
