package com.example.appsicenet.ui.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appsicenet.navegation.NavigationScreens

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun KardexScreen(navController: NavController, viewModel: ProfileViewModel) {
//    val kardex = viewModel.kardex
//
//    if (kardex != null) {
//        Column(modifier = Modifier.padding(16.dp)) {
//            // Itera sobre cada elemento del kardex y muestra la información en la pantalla
//            kardex.lstKardex.forEach { kardexItem ->
//                Text(
//                    text = buildAnnotatedString {
//                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
//                            append("Clave Materia: ")
//                        }
//                        append(kardexItem.clvMat)
//                        append(", ")
//                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
//                            append("Materia: ")
//                        }
//                        append(kardexItem.materia)
//                        append(", ")
//                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
//                            append("Créditos: ")
//                        }
//                        append(kardexItem.cdts.toString())
//                        append(", ")
//                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
//                            append("Calificación: ")
//                        }
//                        append(kardexItem.calif.toString())
//                    },
//                    modifier = Modifier.padding(vertical = 4.dp)
//                )
//            }
//
//            // Muestra el promedio general
//            Text(
//                text = "Promedio General: ${kardex.promedio.promedioGral}",
//                fontWeight = FontWeight.Bold,
//                modifier = Modifier.padding(vertical = 8.dp)
//            )
//
//            // Botón para cerrar sesión
//            Button(onClick = { navController.navigate(NavigationScreens.LoginScreen.route) }) {
//                Text(text = "Cerrar sesión")
//            }
//        }
//    } else {
//        // Si el kardex es nulo, muestra un mensaje de error
//        Column(modifier = Modifier.padding(16.dp)) {
//            Text(text = "No se pudieron obtener las calificaciones")
//        }
//    }
}