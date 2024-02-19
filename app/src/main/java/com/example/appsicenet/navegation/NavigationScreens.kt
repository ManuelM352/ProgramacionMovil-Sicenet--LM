package com.example.appsicenet.navegation

sealed class NavigationScreens (val route: String){
    data object LoginScreen:NavigationScreens("login")
}