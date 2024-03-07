package com.example.appsicenet.ui.screens

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddReaction
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.appsicenet.navegation.NavigationScreens
import androidx.compose.ui.platform.LocalContext
import com.example.appsicenet.models.CalificacionesFinales
import com.example.appsicenet.models.EnvelopeCalf
import com.example.appsicenet.workers.WorkerState
import kotlinx.serialization.json.Json

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileScreen(navController: NavController, viewModel: ProfileViewModel) {
    val attributes = viewModel.attributes
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Perfil") },
                actions = {
                    IconButton(onClick = {
                        viewModel.getCalificacionesUnidades()
                        navController.navigate("calfUnidades")
                    }) {
                        Icon(Icons.Default.Person, contentDescription = "CalificacionesUni")
                    }
                    IconButton(onClick = {
                        viewModel.getCargaAcademica()
                        navController.navigate("cargaAcademica")

                    }) {
                        Icon(Icons.Default.Settings, contentDescription = "CargaAcademica")
                    }
                    IconButton(onClick = {
                        viewModel.getCalificacionesFinales()
                        navController.navigate("calfFinal")
                    }) {
                        Icon(Icons.Default.Add, contentDescription = "CalificacionesFinales")
                    }
                    IconButton(onClick = {
                        viewModel.getKardex()
                        navController.navigate("kardex")
                    }) {
                        Icon(Icons.Default.AddReaction, contentDescription = "Kardex")
                    }
                }
            )
        }
    ) {
        if (attributes != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "${attributes.nombre}",
                    style = androidx.compose.ui.text.TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Text(
                    text = "${attributes.carrera}",
                    style = androidx.compose.ui.text.TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Text(
                    text = "${attributes.especialidad}",
                    style = androidx.compose.ui.text.TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = {
                        navController.navigate(NavigationScreens.LoginScreen.route)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Cerrar sesión")
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "No se pudo obtener el perfil académico.",
                    style = androidx.compose.ui.text.TextStyle(fontSize = 20.sp)
                )
            }
        }
        when {
            viewModel.calfFinalesSyncWorkerState.value == WorkerState.SUCCESS &&
                    viewModel.calfUnidadesSyncWorkerState.value == WorkerState.SUCCESS &&
                        viewModel.cargaAcademicaSyncWorkerState.value == WorkerState.SUCCESS &&
                            viewModel.kardexSyncWorkerState.value == WorkerState.SUCCESS &&
                                viewModel.promedioSyncWorkerState.value == WorkerState.SUCCESS -> {
                // WORKERS se completaron con éxito
                showInfo(context, "Sincronización de datos completada.")
            }

            else -> {
            }
        }
    }
}

fun showInfo(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}
