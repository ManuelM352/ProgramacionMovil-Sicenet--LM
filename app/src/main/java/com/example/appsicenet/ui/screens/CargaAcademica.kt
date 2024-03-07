package com.example.appsicenet.ui.screens

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.appsicenet.models.CargaAcademica
import com.example.appsicenet.navegation.NavigationScreens
import kotlinx.coroutines.launch
import kotlin.math.log
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CargaAcademica(navController: NavController, viewModel: ProfileViewModel, calificaciones: List<CargaAcademica>?) {
    val coroutineScope = rememberCoroutineScope()
    if (!calificaciones.isNullOrEmpty()) {
        LazyColumn(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            items(calificaciones) { calificacion ->
                // Representa cada atributo de la calificación final en un Text
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .background(color = Color.LightGray)
                        .fillMaxWidth()
                ) {
                    Text(text = "semipresencial: ${calificacion.semipresencial}")
                    Text(text = "observaciones: ${calificacion.observaciones}")
                    Text(text = "Grupo: ${calificacion.grupo}")
                    Text(text = "Materia: ${calificacion.materia}")
                    Text(text = "Observaciones: ${calificacion.docente}")
                }
                // Agrega un separador entre cada calificación final
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            Log.d("SaveButton", "Save button clicked")
                            //viewModeld.saveItem()
                        }
                    },
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = "Guardar")
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                Button(
                    onClick = { navController.navigate(NavigationScreens.LoginScreen.route) },
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = "Cerrar sesión")
                }
            }
        }
        Text(
            text = "${calificaciones.firstOrNull()?.fecha ?: ""}",
            style = TextStyle(fontSize = 16.sp, color = Color.Gray),
            modifier = Modifier.padding(top = 16.dp)
        )
    } else {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "No se pudieron obtener las calificaciones")
        }
    }
}