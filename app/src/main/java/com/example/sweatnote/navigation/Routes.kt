package com.example.sweatnote.navigation

sealed class Routes (val route: String) {
    object Main : Routes("Main")
    object Writing : Routes("Writing")
    object Statistics : Routes("Statistics")
    object Search : Routes("Search")

    object Written : Routes("Written")
}