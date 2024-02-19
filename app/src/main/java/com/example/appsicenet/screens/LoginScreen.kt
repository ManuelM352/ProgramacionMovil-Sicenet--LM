package com.example.appsicenet.screens

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
import com.example.appsicenet.data.RetrofitClient
import com.example.appsicenet.models.AccessLoginResponse
import com.example.appsicenet.models.LoginResult
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun LoginScreen() {
    var matricula by remember { mutableStateOf("") }
    var contrasenia by remember { mutableStateOf("") }
    val context = LocalContext.current

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

                authenticate(context,matricula, contrasenia) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Iniciar sesión")
        }
    }
}


private fun authenticate(context: Context, matricula: String, contrasenia: String) {
    val bodyLogin = loginRequestBody(matricula, contrasenia)
    val service = RetrofitClient(context).retrofitService

    service.login(bodyLogin).enqueue(object : Callback<ResponseBody> {
        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>){
            if (response.isSuccessful) {

                // Obtener la cadena de respuesta completa
                val responseBodyString = response.body()?.toString()

                // Imprimir la cadena de respuesta en el log
                Log.w("Respuesta completa", responseBodyString ?: "Cuerpo de respuesta vacío")

                // Deserializar la respuesta JSON
                val json = Json { ignoreUnknownKeys = true } // Configuración opcional según tus necesidades

                try {
                    val accessLoginResponse = json.decodeFromString<AccessLoginResponse>(responseBodyString ?: "")
                    // Aquí puedes manejar el objeto deserializado según tus necesidades
                    Log.w("Objeto deserializado", accessLoginResponse.toString())

                    val accessResultJson: String? = accessLoginResponse.body?.accesoLoginResponse?.accesoLoginResult
                    if (!accessResultJson.isNullOrBlank()) {
                        val accessResult: LoginResult = json.decodeFromString(accessResultJson)

                    } else {
                        showError(context, "Error: La cadena JSON de accesoLoginResult está vacía o nula")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    showError(context, "Error en la deserialización de la respuesta")
                }

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


private fun loginRequestBody(matricula: String, contrasenia: String): RequestBody {
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

//public fun profileRequestBody(): RequestBody {
//    return """
//        <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
//          <soap:Body>
//            <getAlumnoAcademicoWithLineamiento xmlns="http://tempuri.org/" />
//          </soap:Body>
//        </soap:Envelope>
//    """.trimIndent().toRequestBody("text/xml; charset=utf-8".toMediaTypeOrNull())
//}

private fun showError(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}
