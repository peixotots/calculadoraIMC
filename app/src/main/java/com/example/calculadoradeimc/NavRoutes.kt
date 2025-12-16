package com.example.calculadoradeimc

sealed class NavRoutes(val route: String) {
    object Home : NavRoutes("home")
    object History : NavRoutes("history")
    object Detail : NavRoutes("detail/{id}")

    object Help : NavRoutes("help")

    fun detailRoute(id: Long) = "detail/$id"
}