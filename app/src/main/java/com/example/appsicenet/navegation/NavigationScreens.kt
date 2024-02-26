package com.example.appsicenet.navegation

sealed class NavigationScreens (val route: String){
    data object LoginScreen:NavigationScreens("login")
    data object CalifFinalScreen :NavigationScreens("calfFinal")
    data object CalifUnidadesScreen :NavigationScreens("calfUnidades")
    data object KardexScreen :NavigationScreens("kardex")
}
