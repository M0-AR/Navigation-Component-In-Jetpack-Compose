package com.example.navigationcomponentinjetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.navigationcomponentinjetpackcompose.ui.theme.NavigationComponentInJetpackComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationComponentInJetpackComposeTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "login"
                    ) {
                        composable("login") {
                            val viewModel: LoginViewModel by viewModels()
                            Login(navController, viewModel)
                        }
                        composable(
                            "profile/{name}",
                            arguments = listOf(navArgument("name") {
                                type = NavType.StringType
                            })
                        ) { backStackEntry ->
                            Profile(
                                navController,
                                backStackEntry.arguments?.getString("name") ?: "User"
                            )
                        }
                        composable("friends") {
                            Friends(navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Login(navController: NavController, viewModel: LoginViewModel) {
    val name by viewModel.name
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Login Screen", style = MaterialTheme.typography.h4)
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = name,
            onValueChange = { viewModel.onNameChange(it) },
            label = { Text("Enter your name") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val destinationName = if (name.isBlank()) "Guest" else name
                navController.navigate("profile/$destinationName") {
                    popUpTo("login") { inclusive = true }
                }
            },
        ) {
            Text("Go to profile")
        }
    }
}

@Composable
fun Profile(navController: NavController, name: String) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Profile: Hello, $name!", style = MaterialTheme.typography.h5)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("friends") }) {
            Text("Go to Friends")
        }
    }
}

@Composable
fun Friends(navController: NavController) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Friends List", style = MaterialTheme.typography.h5)
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                navController.navigate("login") {
                    popUpTo("friends") { inclusive = true }
                }
            },
        ) {
            Text("Back to Login")
        }
    }
}
