package com.example.sweatnote.navigation

import Search
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.example.sweatnote.Calender.CalendarScreen
import com.example.sweatnote.graphViews.StatsScreen
import com.example.sweatnote.screens.Statistics
import com.example.sweatnote.screens.Writing
import com.example.sweatnote.screens.Written
import com.example.sweatnote.example.DiaryViewModel
import com.example.sweatnote.roomDB.Diary

@Composable
fun NavGraph(navController: NavHostController, diaryViewModel: DiaryViewModel) {
    NavHost(navController = navController, startDestination = Routes.Main.route) {
        composable(route = Routes.Main.route) {
            CalendarScreen(navController)
        }

        composable(route = Routes.Search.route) {
            Search(navController)
        }

        composable(route = Routes.Statistics.route) {
            StatsScreen(navController)
        }

        composable(
            route = "${Routes.Writing.route}/{date}",
            arguments = listOf(navArgument("date") { type = NavType.StringType })
        ) { backStackEntry ->
            val date = backStackEntry.arguments?.getString("date") ?: ""
            Writing(navController, diaryViewModel, date)
        }

        composable(
            route = Routes.Written.route + "/{diary}",
            arguments = listOf(navArgument("diary") { type = NavType.ParcelableType(Diary::class.java) })
        ) { backStackEntry ->
            val diary = backStackEntry.arguments?.getParcelable<Diary>("diary")
            if (diary != null) {
                Written(navController, diaryViewModel, diary)
            }
        }
    }
}
