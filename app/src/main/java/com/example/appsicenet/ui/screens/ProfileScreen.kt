package com.example.appsicenet.ui.screens

import android.annotation.SuppressLint
import android.os.Build
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
//                ProfileAttribute("Matrícula", attributes.matricula)
//                ProfileAttribute("Carrera", attributes.carrera)
//                ProfileAttribute("Especialidad", attributes.especialidad)
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
//
//private fun getCalificaciones(context: Context, navController: NavController, viewModel: ProfileViewModel) {
//    val service = RetrofitClient(context).retrofitService
//    val bodyProfile = profileRequestBody()
//
//    service.getAcademicProfile(bodyProfile).enqueue(object : Callback<Envelope> {
//        @Composable
//        override fun onResponse(call: Call<Envelope>, response: Response<Envelope>) {
//            if (response.isSuccessful) {
//                val envelope = response.body()
//                val alumnoResultJson: String? = envelope?.body?.getAlumnoAcademicoWithLineamientoResponse?.getAlumnoAcademicoWithLineamientoResult
//
//                // Deserializa la cadena JSON a objeto Attributes
//                val json = Json { ignoreUnknownKeys = true }
//                val alumnoAcademicoResult: Attributes? = alumnoResultJson?.let { json.decodeFromString(it) }
//
//                // Verifica si se obtuvieron los datos del alumno
//                if (alumnoAcademicoResult != null) {
//                    Log.w("Exito", "Se obtuvieron los datos del alumno: $alumnoAcademicoResult")
//
//                    // Ahora, podrías llamar a una función para obtener las calificaciones finales
//                    //getCalificacionesFinales(context, navController, viewModel)
//                    //getCalificacionesFinales(context, navController, viewModel)
//                    //getKardexProfile(context, navController, viewModel)
//                    viewModel.getCalificacionesFinales()
//                    //-----------------------------------------------------------------------------------------------------------
//                    cargaAcademica(navController, viewModel )
//                //getCargaAcademica(context, navController, viewModel)
//
//                } else {
//                    showError(context, "No se pudieron obtener los datos del alumno.")
//                }
//            } else {
//                showError(context, "Error al obtener los datos del alumno. Código de respuesta: ${response.code()}")
//            }
//        }
//
//        override fun onFailure(call: Call<Envelope>, t: Throwable) {
//            t.printStackTrace()
//            showError(context, "Error en la solicitud de datos del alumno")
//        }
//    })
//}




@Composable
fun cargaAcademica(navController: NavController, viewModel: ProfileViewModel){

    when (viewModel.sicenetUiState){
        SicenetUiState.Loading -> Unit
        SicenetUiState.Success -> {
            viewModel.cargaAcademica
            navController.navigate("cargaAcademica")
        }

        else -> {
            val context = LocalContext.current
            showError(context, "Error al obtener el perfil académico.")
        }
    }
}
//
//private fun getCalificacionesFinales(context: Context, navController: NavController, viewModel: ProfileViewModel) {
//    val service = RetrofitClient(context).retrofitService
//    val bodyCalificaciones = califfinalRequestBody() // Asume que ya tienes esta función
//
//    service.getCalifFinal(bodyCalificaciones).enqueue(object : Callback<EnvelopeCalf> {
//        override fun onResponse(call: Call<EnvelopeCalf>, response: Response<EnvelopeCalf>) {
//            if (response.isSuccessful) {
//                val calificacionesEnvelope = response.body()
//                val alumnoResultJson: String? = calificacionesEnvelope?.bodyCalf?.getAllCalifFinalByAlumnosResponse?.getAllCalifFinalByAlumnosResult
//
//                Log.w("Exito", " ${alumnoResultJson}")
//
//                // Aquí puedes manejar la respuesta de las calificaciones finales
//                if (alumnoResultJson != null) {
//                    /// Llamar al método para procesar las calificaciones y actualizar el ViewModel
//                    val calificaciones = parseCalificacionesFinales(calificacionesEnvelope)
//                    viewModel.calificacionesFinales = calificaciones // Asigna la lista de calificaciones al ViewModel
//                    // Navega a la pantalla de calificaciones finales
//                    navController.navigate("calfFinal")
//                } else {
//                    showError(context, "No se pudieron obtener las calificaciones finales.")
//                }
//            } else {
//                showError(context, "Error al obtener las calificaciones finales. Código de respuesta: ${response.code()}")
//            }
//        }
//
//        override fun onFailure(call: Call<EnvelopeCalf>, t: Throwable) {
//            t.printStackTrace()
//            showError(context, "Error en la solicitud de las calificaciones finales")
//        }
//    })
//}

fun parseCalificacionesFinales(envelope: EnvelopeCalf): List<CalificacionesFinales> {
    val resultJson = envelope.bodyCalf.getAllCalifFinalByAlumnosResponse.getAllCalifFinalByAlumnosResult
    val json = Json { ignoreUnknownKeys = true } // Configura el parser para ignorar claves desconocidas
    return json.decodeFromString(resultJson)
}

//
//private fun getCalificacionesUnidades(context: Context, navController: NavController, viewModel: ProfileViewModel) {
//    val service = RetrofitClient(context).retrofitService
//    val bodyCalificaciones = califUnidadesRequestBody() // Asume que ya tienes esta función
//
//    service.getCalifUnidades(bodyCalificaciones).enqueue(object : Callback<EnvelopeCalfUni> {
//        override fun onResponse(call: Call<EnvelopeCalfUni>, response: Response<EnvelopeCalfUni>) {
//            if (response.isSuccessful) {
//                val calificacionesEnvelope = response.body()
//                val alumnoResultJson: String? = calificacionesEnvelope?.bodyCalfUni?.getCalifUnidadesByAlumnoResponse?.getCalifUnidadesByAlumnoResult
//
//                Log.w("Exito", " ${alumnoResultJson}")
//
//                // Aquí puedes manejar la respuesta de las calificaciones finales
//                if (alumnoResultJson != null) {
//                    /// Llamar al método para procesar las calificaciones y actualizar el ViewModel
//                    val calificaciones = parseCalificacionesUnidades(calificacionesEnvelope)
//                    viewModel.calificacionesUnidades = calificaciones // Asigna la lista de calificaciones al ViewModel
//                    // Navega a la pantalla de calificaciones finales
//                    navController.navigate("calfUnidades")
//                } else {
//                    showError(context, "No se pudieron obtener las calificaciones.")
//                }
//            } else {
//                showError(context, "Error al obtener las calificaciones. Código de respuesta: ${response.code()}")
//            }
//        }
//
//        override fun onFailure(call: Call<EnvelopeCalfUni>, t: Throwable) {
//            t.printStackTrace()
//            showError(context, "Error en la solicitud de las calificaciones")
//        }
//    })
//}
//
//fun parseCalificacionesUnidades(envelope: EnvelopeCalfUni): List<CalificacionUnidades> {
//    val resultJson = envelope.bodyCalfUni.getCalifUnidadesByAlumnoResponse.getCalifUnidadesByAlumnoResult
//    val json = Json { ignoreUnknownKeys = true } // Configura el parser para ignorar claves desconocidas
//    return json.decodeFromString(resultJson)
//}
//
//
//private fun getKardexProfile(context: Context, navController: NavController, viewModel: ProfileViewModel) {
//    val service = RetrofitClient(context).retrofitService
//    val bodyProfile = kardexRequestBody()
//    service.getKardex(bodyProfile).enqueue(object : Callback<EnvelopeKardex> {
//        override fun onResponse(call: Call<EnvelopeKardex>, response: Response<EnvelopeKardex>) {
//            if (response.isSuccessful) {
//                val envelope = response.body()
//                if (envelope != null) {
//                    val alumnoResultJson: String? = envelope.bodyKardex?.getAllKardexConPromedioByAlumnoResponse?.getAllKardexConPromedioByAlumnoResult
//                    // Deserializa la cadena JSON
//                    if (alumnoResultJson != null) {
//                        val json = Json { ignoreUnknownKeys = true; coerceInputValues = true }
//                        val kardex: Kardex = json.decodeFromString(alumnoResultJson)
//                        // Imprime las calificaciones en el log
////                        for (kardexItem in kardex.lstKardex) {
////                            Log.d("Kardex", "Clave Materia: ${kardexItem.clvMat}, Clave Oficial Materia: ${kardexItem.clvOfiMat}, Materia: ${kardexItem.materia}, Créditos: ${kardexItem.cdts}, Calificación: ${kardexItem.calif}, Acreditación: ${kardexItem.acred}, Semestre 1: ${kardexItem.s1}, Periodo 1: ${kardexItem.p1}, Año 1: ${kardexItem.a1}, Semestre 2: ${kardexItem.s2}, Periodo 2: ${kardexItem.p2}, Año 2: ${kardexItem.a2}")
////                        }
//
//                        val promedio = kardex.promedio
////                        Log.d("Promedio", "Promedio General: ${promedio.promedioGral}, Créditos Acumulados: ${promedio.cdtsAcum}, Créditos Plan: ${promedio.cdtsPlan}, Materias Cursadas: ${promedio.matCursadas}, Materias Aprobadas: ${promedio.matAprobadas}, Avance Créditos: ${promedio.avanceCdts}")
//                        viewModel.kardex=kardex
//                        //getCargaAcadProfile(context, navController, viewModel)
//                        navController.navigate("kardex")
//                    } else {
//                        showError(context, "La respuesta es nula. No se pudo obtener el kardex.")
//                    }
//                } else {
//                    showError(context, "La respuesta del servidor es nula. No se pudo obtener el kardex.")
//                }
//            } else {
//                showError(context, "Error al obtener el kardex. Código de respuesta: ${response.code()}")
//            }
//        }
//        override fun onFailure(call: Call<EnvelopeKardex>, t: Throwable) {
//            t.printStackTrace()
//            showError(context, "Error en la solicitud del perfil académico")
//        }
//    })
//}
//
//
//private fun getCargaAcademica(context: Context, navController: NavController, viewModel: ProfileViewModel) {
//    val service = RetrofitClient(context).retrofitService
//    val bodyCalificaciones = cargaAcademicaRequestBody() // Asume que ya tienes esta función
//
//    service.getCargaAcademica(bodyCalificaciones).enqueue(object : Callback<EnvelopeCargaAcademica> {
//        override fun onResponse(call: Call<EnvelopeCargaAcademica>, response: Response<EnvelopeCargaAcademica>) {
//            if (response.isSuccessful) {
//                val calificacionesEnvelope = response.body()
//                val alumnoResultJson: String? = calificacionesEnvelope?.bodyCargaAcademica?.getCargaAcademicaByAlumnoResponse?.getCargaAcademicaByAlumnoResult
//
//                Log.w("Exito", " ${alumnoResultJson}")
//
//                // Aquí puedes manejar la respuesta de las calificaciones finales
//                if (alumnoResultJson != null) {
//                    /// Llamar al método para procesar las calificaciones y actualizar el ViewModel
//                    val calificaciones = parseCargaAcademica(calificacionesEnvelope)
//                    viewModel.cargaAcademica = calificaciones // Asigna la lista de calificaciones al ViewModel
//                    // Navega a la pantalla de calificaciones finales
//                    navController.navigate("cargaAcademica")
//                } else {
//                    showError(context, "No se pudieron obtener las calificaciones.")
//                }
//            } else {
//                showError(context, "Error al obtener las calificaciones. Código de respuesta: ${response.code()}")
//            }
//        }
//
//        override fun onFailure(call: Call<EnvelopeCargaAcademica>, t: Throwable) {
//            t.printStackTrace()
//            showError(context, "Error en la solicitud de las calificaciones")
//        }
//    })
//}
//
//fun parseCargaAcademica(envelope: EnvelopeCargaAcademica): List<CargaAcademica> {
//    val resultJson = envelope.bodyCargaAcademica.getCargaAcademicaByAlumnoResponse.getCargaAcademicaByAlumnoResult
//    val json = Json { ignoreUnknownKeys = true } // Configura el parser para ignorar claves desconocidas
//    return json.decodeFromString(resultJson)
//}
