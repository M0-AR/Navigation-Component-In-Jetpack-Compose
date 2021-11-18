package com.example.navigationcomponentinjetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "login"
                    ) {
                        composable("login") {
                            val viewModel by viewModels<LoginViewModel> {
                                it.defaultViewModelProviderFactory
                            }

                            //Login(navController, viewModel)
                            Login(navController)
                        }
                        composable(
                            "profile/{name}",
                            arguments = listOf(navArgument(
                                "name"
                            ) {
                                type = NavType.StringType
                            })
                        ) {
                            Profile(
                                navController,
                                it.arguments?.getString("name") ?: "My name"
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
fun Login(navController: NavController) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Login")

        Button(
            onClick = {
                navController.navigate("profile/MD") {
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
        Text(text = "Profile: Hello, $name")

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
        Text(text = "Friends")
        Button(
            onClick = {
                navController.navigate("profile/{name}") {
                    launchSingleTop = true
                    popUpTo("friends") { inclusive = true }
                }
            },
        ) {
            Text("Go to Profile")
        }
    }
}

@Composable
fun Greeting(name: String) {
    val navController = rememberNavController()

    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NavigationComponentInJetpackComposeTheme {
        Greeting("Android")
    }
}