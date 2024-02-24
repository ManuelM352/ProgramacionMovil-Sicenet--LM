package com.example.appsicenet.navegation

sealed class NavigationScreens (val route: String){
    data object LoginScreen:NavigationScreens("login")
}
/*Text(
                    text = "${attributes.nombre}",
                    style = androidx.compose.ui.text.TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(bottom = 16.dp)
                )*/