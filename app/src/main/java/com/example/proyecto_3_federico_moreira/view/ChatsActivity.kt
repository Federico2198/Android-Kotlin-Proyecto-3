package com.example.proyecto_3_federico_moreira.view


import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Observer
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.proyecto_3_federico_moreira.R
import com.example.proyecto_3_federico_moreira.view.navigation.NavItems
import com.example.proyecto_3_federico_moreira.viewmodel.HomeViewModel

 class ChatsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Variable para el Home View Model //
        val viewModel = HomeViewModel()

        // Referencia del viewModel al Event Listener &&
        viewModel.chatListener

        // Observer del viewModel //
        viewModel.isLoggedIn.observe(this, Observer {
            if(!it){
                this.finish()
            }
        })

        setContent {

            // Set Content para bottomNavigationBar //
            val navController = rememberNavController()
                Scaffold(bottomBar = {
                    BottomNavigationBar(navController = navController)
                }, content = {paddingValues,  ->
                    NavHostContainer(
                        navController = navController,
                        paddingValues = paddingValues
                    ) {
                        viewModel.logout()
                    }
                })
            }
        }
    }

// Composable para el boton de Inicio //
@Composable
fun Inicio() {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(60.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = "Inicio",
            style = TextStyle(fontSize = 36.sp))
    }
}

// Composable para el boton de Chats //
@Composable
fun Chats() {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(60.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(text = "Chats",
            style = TextStyle(fontSize = 36.sp))
    }
}

// Conposable para la Navegacion (Inicio, Chats y Salir)
@Composable
fun NavHostContainer(
    navController: NavHostController,
    paddingValues: PaddingValues,
    logout:()->Unit
){
    NavHost(
        navController = navController,
        startDestination = "inicio",
        modifier = Modifier.padding(paddingValues = paddingValues),
        builder = {
            composable("inicio") {
                Inicio()
            }
            composable("chats") {
                Chats()
            }
            composable("salir") {
                logout()
            }
        }
    )
}

// Composable para el BottomNavigationBar //
@Composable
fun BottomNavigationBar(navController: NavHostController) {
    BottomNavigation(
        backgroundColor = colorResource(id = R.color.purple_500)) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        NavItems.NavItems.forEach { navIt ->
            BottomNavigationItem(selected = currentRoute == navIt.route,
                onClick = {
                    navController.navigate(navIt.route)
                },
                icon = {
                    Icon(
                        imageVector = navIt.icon,
                        contentDescription = navIt.label
                    )},
                alwaysShowLabel = false
            )
        }
    }
}