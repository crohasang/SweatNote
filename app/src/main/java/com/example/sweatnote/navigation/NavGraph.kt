package com.example.sweatnote.navigation

import Search
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.sweatnote.Calender.CalendarScreen
import com.example.sweatnote.screens.*
import com.example.sweatnote.example.DiaryViewModel

@Composable
fun NavGraph(navController: NavHostController, diaryViewModel: DiaryViewModel) {
    NavHost(navController = navController, startDestination = Routes.Main.route) {
        composable(Routes.Main.route) {
            CalendarScreen(navController)
        }
        composable(Routes.Search.route) {
            Search(navController)
        }
        composable(Routes.Statistics.route) {
            Statistics(navController, diaryViewModel)
        }
        composable(Routes.Writing.route + "/{date}") { backStackEntry ->
            val date = backStackEntry.arguments?.getString("date") ?: ""
            Writing(navController, diaryViewModel, date)
        }
        composable(Routes.Written.route + "/{date}") { backStackEntry ->
            val date = backStackEntry.arguments?.getString("date") ?: ""
            Written(navController, diaryViewModel, date)
        }
    }
}
