package com.example.calculadoradeimc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.calculadoradeimc.data.AppDatabase
import com.example.calculadoradeimc.data.MeasurementRepository
import com.example.calculadoradeimc.view.DetailScreen
import com.example.calculadoradeimc.view.Home
import com.example.calculadoradeimc.view.HistoryScreen
import com.example.calculadoradeimc.viewmodel.HomeViewModel
import com.example.calculadoradeimc.viewmodel.HomeViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppNavHost()
        }
    }
}

@Composable
private fun AppNavHost() {
    val ctx = LocalContext.current
    val db = AppDatabase.getInstance(ctx)
    val repo = MeasurementRepository(db.measurementDao())
    val factory = HomeViewModelFactory(repo)
    val vm: HomeViewModel = viewModel(factory = factory)

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavRoutes.Home.route) {
        composable(NavRoutes.Home.route) {
            Home(
                viewModel = vm,
                onNavigateToHistory = { navController.navigate(NavRoutes.History.route) }
            )
        }

        composable(NavRoutes.History.route) {
            HistoryScreen(
                repo = repo,
                onNavigateToDetail = { id ->
                    navController.navigate(
                        NavRoutes.Detail.route.replace(
                            "{id}",
                            id.toString()
                        )
                    )
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = NavRoutes.Detail.route,
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id") ?: 0L
            DetailScreen(
                repo = repo,
                id = id,
                onBack = { navController.popBackStack() }
            )
        }
    }
}