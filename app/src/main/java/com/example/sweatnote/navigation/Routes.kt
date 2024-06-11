package com.example.sweatnote.navigation

sealed class Routes(val route: String) {
    object Main : Routes("Main")
    object Writing : Routes("Writing/{date}") {
        fun createRoute(date: String) = "Writing/$date"
    }
    object Written : Routes("Written/{date}") {
        fun createRoute(date: String) = "Written/$date"
    }
    object Statistics : Routes("Statistics")
    object Search : Routes("Search")
}
