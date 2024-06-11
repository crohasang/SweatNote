package com.example.sweatnote.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.sweatnote.Calender.CalendarScreen
import com.example.sweatnote.example.DiaryViewModel
import com.example.sweatnote.screens.*

@Composable
fun NavGraph(navController: NavHostController, diaryViewModel: DiaryViewModel) {
    NavHost(navController = navController, startDestination = Routes.Main.route) {
        composable(route = Routes.Main.route) {
            CalendarScreen(navController)
        }
        composable(route = Routes.Writing.route + "/{date}") { backStackEntry ->
            val date = backStackEntry.arguments?.getString("date") ?: ""
            Writing(navController, diaryViewModel, date)
        }
        composable(route = Routes.Statistics.route) {
            Statistics(navController, diaryViewModel)
        }
        composable(route = Routes.Search.route) {
            Search(navController)
        }
        composable(route = Routes.Written.route + "/{date}") { backStackEntry ->
            val date = backStackEntry.arguments?.getString("date") ?: ""
            Written(navController, diaryViewModel, date)
        }
    }
}
