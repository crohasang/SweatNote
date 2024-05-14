package com.example.sweatnote.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.sweatnote.screens.Main
import com.example.sweatnote.screens.Search
import com.example.sweatnote.screens.Statistics
import com.example.sweatnote.screens.Writing

@Composable
fun NavGraph(navController: NavHostController) {

    NavHost(navController=navController, startDestination = Routes.Main.route){

        composable(route=Routes.Main.route){
            Main(navController)
        }

        composable(route=Routes.Search.route){
            Search(navController)
        }

        composable(route=Routes.Statistics.route){
            Statistics(navController)
        }

        composable(route=Routes.Writing.route){
            Writing(navController)
        }

    }

}