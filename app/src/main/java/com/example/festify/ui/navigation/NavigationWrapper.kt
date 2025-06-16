package com.example.festify.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.festify.ui.screens.MainHomeScreen
import com.example.festify.ui.screens.MainLoginScreen
import com.example.festify.ui.screens.MainRegisterScreen

@Composable
fun NavigationWrapper(){
    val navController = rememberNavController()

    NavHost(
        navController = navController, startDestination = Login
    ) {
        //navegacion entre screens

        composable<Login>{
            MainLoginScreen(navController = navController)
        }
        composable<Register>{
            MainRegisterScreen(navController = navController)

        }
        composable<Home>{
            MainHomeScreen(navController = navController)
        }
    }
}