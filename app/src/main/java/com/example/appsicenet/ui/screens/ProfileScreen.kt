package com.example.appsicenet.ui.screens

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.appsicenet.models.Attributes
import com.example.appsicenet.navegation.NavigationScreens
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.appsicenet.data.RetrofitClient
import com.example.appsicenet.models.CalificacionesFinales
import com.example.appsicenet.models.Envelope
import com.example.appsicenet.models.EnvelopeCalf
import com.example.appsicenet.network.AddCookiesInterceptor
import com.example.appsicenet.network.califfinalRequestBody
import com.example.appsicenet.network.profileRequestBody
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.format.TextStyle

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
                        navController.navigate("login")
                    }) {
                        Icon(Icons.Default.Person, contentDescription = "Login")
                    }
                    IconButton(onClick = {
                        getCalificaciones(context, navController,viewModel)
                    }) {
                        Icon(Icons.Default.Settings, contentDescription = "Perfil")
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
                ProfileAttribute("Matrícula", attributes.matricula)
                ProfileAttribute("Carrera", attributes.carrera)
                ProfileAttribute("Especialidad", attributes.especialidad)
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
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileAttribute(label: String, value: String) {
    Column {
        Text(
            text = label,
            style = androidx.compose.ui.text.TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Text(
            text = value,
            style = androidx.compose.ui.text.TextStyle(fontSize = 16.sp)
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

private fun getCalificaciones(context: Context, navController: NavController, viewModel: ProfileViewModel) {
    val service = RetrofitClient(context).retrofitService
    val bodyProfile = profileRequestBody()

    service.getAcademicProfile(bodyProfile).enqueue(object : Callback<Envelope> {
        override fun onResponse(call: Call<Envelope>, response: Response<Envelope>) {
            if (response.isSuccessful) {
                val envelope = response.body()
                val alumnoResultJson: String? = envelope?.body?.getAlumnoAcademicoWithLineamientoResponse?.getAlumnoAcademicoWithLineamientoResult

                // Deserializa la cadena JSON a objeto Attributes
                val json = Json { ignoreUnknownKeys = true }
                val alumnoAcademicoResult: Attributes? = alumnoResultJson?.let { json.decodeFromString(it) }

                // Verifica si se obtuvieron los datos del alumno
                if (alumnoAcademicoResult != null) {
                    Log.w("Exito", "Se obtuvieron los datos del alumno: $alumnoAcademicoResult")

                    // Ahora, podrías llamar a una función para obtener las calificaciones finales
                    obtenerCalificacionesFinales(context, navController, viewModel)
                } else {
                    showError(context, "No se pudieron obtener los datos del alumno.")
                }
            } else {
                showError(context, "Error al obtener los datos del alumno. Código de respuesta: ${response.code()}")
            }
        }

        override fun onFailure(call: Call<Envelope>, t: Throwable) {
            t.printStackTrace()
            showError(context, "Error en la solicitud de datos del alumno")
        }
    })
}

private fun obtenerCalificacionesFinales(context: Context, navController: NavController, viewModel: ProfileViewModel) {
    val service = RetrofitClient(context).retrofitService
    val bodyCalificaciones = califfinalRequestBody() // Asume que ya tienes esta función

    service.getCalifFinal(bodyCalificaciones).enqueue(object : Callback<EnvelopeCalf> {
        override fun onResponse(call: Call<EnvelopeCalf>, response: Response<EnvelopeCalf>) {
            if (response.isSuccessful) {
                val calificacionesEnvelope = response.body()
                val alumnoResultJson: String? = calificacionesEnvelope?.bodyCalf?.getAllCalifFinalByAlumnosResponse?.getAllCalifFinalByAlumnosResult

                Log.w("Exito", " ${alumnoResultJson}")

                // Aquí puedes manejar la respuesta de las calificaciones finales
                if (alumnoResultJson != null) {
                    /// Llamar al método para procesar las calificaciones y actualizar el ViewModel
                    val calificaciones = parseCalificacionesFinales(calificacionesEnvelope)
                    viewModel.calificacionesFinales = calificaciones // Asigna la lista de calificaciones al ViewModel
                    // Navega a la pantalla de calificaciones finales
                    navController.navigate("calfFinal")
                } else {
                    showError(context, "No se pudieron obtener las calificaciones finales.")
                }
            } else {
                showError(context, "Error al obtener las calificaciones finales. Código de respuesta: ${response.code()}")
            }
        }

        override fun onFailure(call: Call<EnvelopeCalf>, t: Throwable) {
            t.printStackTrace()
            showError(context, "Error en la solicitud de las calificaciones finales")
        }
    })
}

fun parseCalificacionesFinales(envelope: EnvelopeCalf): List<CalificacionesFinales> {
    val resultJson = envelope.bodyCalf.getAllCalifFinalByAlumnosResponse.getAllCalifFinalByAlumnosResult
    val json = Json { ignoreUnknownKeys = true } // Configura el parser para ignorar claves desconocidas
    return json.decodeFromString(resultJson)
}