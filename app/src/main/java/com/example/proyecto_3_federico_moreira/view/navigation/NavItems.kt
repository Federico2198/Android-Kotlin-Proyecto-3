package com.example.proyecto_3_federico_moreira.view.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home

object NavItems {

    val NavItems = listOf(
        NavItem(
            label = "",
            icon = Icons.Default.Home,
            route = "inicio"
        ),
        NavItem(
            label = "",
            icon = Icons.Default.AccountBox,
            route = "chats"
        ),
        NavItem(
            label = "",
            icon = Icons.Default.ExitToApp,
            route = "salir",
        )
    )
}

