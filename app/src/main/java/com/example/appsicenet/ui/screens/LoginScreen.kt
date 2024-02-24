package com.example.appsicenet.ui.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.appsicenet.data.RetrofitClient
import com.example.appsicenet.models.AccessLoginResponse
import com.example.appsicenet.models.Attributes
import com.example.appsicenet.models.Envelope
import com.example.appsicenet.models.LoginResult
import com.example.appsicenet.network.AddCookiesInterceptor
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import okhttp3.internal.addHeaderLenient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun LoginScreen(navController: NavController, viewModel: ProfileViewModel) {
    var matricula by remember { mutableStateOf("") }
    var contrasenia by remember { mutableStateOf("") }
    val context = LocalContext.current
    val marsViewModel: ProfileViewModel =
        viewModel(factory = ProfileViewModel.Factory)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = matricula,
            onValueChange = { matricula = it },
            label = { Text("Matrícula") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = contrasenia,
            onValueChange = { contrasenia = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                authenticate(context,matricula, contrasenia, navController, viewModel) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Iniciar sesión")
        }
    }
}


private fun authenticate(context: Context, matricula: String, contrasenia: String,  navController: NavController , viewModel: ProfileViewModel) {
    val bodyLogin = loginRequestBody(matricula, contrasenia)
    val service = RetrofitClient(context).retrofitService

    service.login(bodyLogin).enqueue(object : Callback<ResponseBody> {
        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>){
            if (response.isSuccessful) {
                val service = RetrofitClient(context).retrofitService
                val bodyProfile = profileRequestBody()
                service.getAcademicProfile(bodyProfile).enqueue(object : Callback<Envelope> {
                    override fun onResponse(call: Call<Envelope>, response: Response<Envelope>) {
                        if (response.isSuccessful) {
                            val envelope = response.body()
                            val alumnoResultJson: String? = envelope?.body?.getAlumnoAcademicoWithLineamientoResponse?.getAlumnoAcademicoWithLineamientoResult

                            // Deserializa la cadena JSON a AlumnoAcademicoResult
                            val json = Json { ignoreUnknownKeys = true }
                            val alumnoAcademicoResult: Attributes? = alumnoResultJson?.let { json.decodeFromString(it) }

                            Log.w("exito", "se obtuvo el perfil 2: ${alumnoAcademicoResult}")

                            getProfile(context, navController,viewModel)
                            

                        } else {
                            showError(context, "Credenciales invalidas")
                        }
                    }
                    override fun onFailure(call: Call<Envelope>, t: Throwable) {
                        t.printStackTrace()
                        showError(context, "Error en la solicitud del perfil académico")
                    }
                })
            } else {
                showError(context, "Error en la autenticación. Código de respuesta: ${response.code()}")
            }
        }

        override fun onFailure(call: Call<ResponseBody>, t: Throwable){
            t.printStackTrace()
            showError(context, "Error en la solicitud")
        }
    })
}

private fun getProfile(context: Context, navController: NavController, viewModel: ProfileViewModel) {
    val service = RetrofitClient(context).retrofitService
    val bodyProfile = profileRequestBody()
    service.getAcademicProfile(bodyProfile).enqueue(object : Callback<Envelope> {
        override fun onResponse(call: Call<Envelope>, response: Response<Envelope>) {
            if (response.isSuccessful) {
                val envelope = response.body()
                val alumnoResultJson: String? = envelope?.body?.getAlumnoAcademicoWithLineamientoResponse?.getAlumnoAcademicoWithLineamientoResult

                val json = Json { ignoreUnknownKeys = true }
                val alumnoAcademicoResult: Attributes? = alumnoResultJson?.let { json.decodeFromString(it) }

                Log.w("Exito", "Se obtuvo el perfil 2: ${alumnoAcademicoResult}")
                val alumnoAcademicoResultJson = Json.encodeToString(alumnoAcademicoResult)

                val addCookiesInterceptor = AddCookiesInterceptor(context)
                addCookiesInterceptor.clearCookies()
                viewModel.attributes=alumnoAcademicoResult
                navController.navigate("profile")
            } else {
                showError(context, "Error al obtener el perfil académico. Código de respuesta: ${response.code()}")
            }
        }
        override fun onFailure(call: Call<Envelope>, t: Throwable) {
            t.printStackTrace()
            showError(context, "Error en la solicitud del perfil académico")
        }
    })
}

fun loginRequestBody(matricula: String, contrasenia: String): RequestBody {
    return """
        <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
          <soap:Body>
            <accesoLogin xmlns="http://tempuri.org/">
              <strMatricula>$matricula</strMatricula>
              <strContrasenia>$contrasenia</strContrasenia>
              <tipoUsuario>ALUMNO</tipoUsuario>
            </accesoLogin>
          </soap:Body>
        </soap:Envelope>
    """.trimIndent().toRequestBody("text/xml; charset=utf-8".toMediaTypeOrNull())
}

private fun profileRequestBody(): RequestBody {
    return """
        <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
          <soap:Body>
            <getAlumnoAcademicoWithLineamiento xmlns="http://tempuri.org/" />
          </soap:Body>
        </soap:Envelope>
    """.trimIndent().toRequestBody("text/xml; charset=utf-8".toMediaTypeOrNull())
}

private fun showError(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}