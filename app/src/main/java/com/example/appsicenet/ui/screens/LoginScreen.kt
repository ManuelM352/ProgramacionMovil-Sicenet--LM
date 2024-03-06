package com.example.appsicenet.ui.screens

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LoginScreen(navController: NavController, viewModel: ProfileViewModel) {
    var matricula by remember { mutableStateOf("") }
    var contrasenia by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val h4TextStyle = androidx.compose.ui.text.TextStyle(
        fontSize = 24.sp, // Tamaño de fuente deseado
        fontWeight = FontWeight.Bold // Peso de la fuente deseado
    )
    //authenticate(context,matricula, contrasenia, navController, viewModel)
    navegacionProfile(navController, viewModel)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Iniciar sesión",
            style = h4TextStyle,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        OutlinedTextField(
            value = matricula,
            onValueChange = { matricula = it },
            label = { Text("Matrícula") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = contrasenia,
                onValueChange = { contrasenia = it },
                label = { Text("Contraseña") },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier.weight(1f)
            )
            IconButton(
                onClick = { passwordVisible = !passwordVisible },
                modifier = Modifier
                    .size(48.dp)
                    .padding(start = 8.dp)
            ) {
                Icon(
                    imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                    contentDescription = "Toggle password visibility"
                )
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                //authenticate(context,matricula, contrasenia, navController, viewModel)
                viewModel.matricula = matricula
                viewModel.contrasenia = contrasenia
                viewModel.performLoginAndFetchAcademicProfile()
                if(viewModel.accesoLoginResult?.acceso==true) {
                    viewModel.getCalificacionesFinales()
                    viewModel.getCalificacionesUnidades()
                    viewModel.getKardex()
                    viewModel.getCargaAcademica()
                }

            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text("Iniciar sesión")
        }
    }
}

@Composable
fun navegacionProfile(navController : NavController, viewModel: ProfileViewModel){
    val context = LocalContext.current
    when (viewModel.sicenetUiState){
        SicenetUiState.Loading -> Unit
        SicenetUiState.Success -> {
            viewModel.attributes
            navController.navigate("profile")
        }

        else -> {
            showError(context, "Error al obtener el perfil académico.")
        }
    }
}



//private fun authenticate(context: Context, matricula: String, contrasenia: String,  navController: NavController , viewModel: ProfileViewModel) {
//    val bodyLogin = loginRequestBody(matricula, contrasenia)
//    val service = RetrofitClient(context).retrofitService
//
//    service.login(bodyLogin).enqueue(object : Callback<ResponseBody> {
//        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>){
//            if (response.isSuccessful) {
//                val service = RetrofitClient(context).retrofitService
//                val bodyProfile = profileRequestBody()
//                service.getAcademicProfile(bodyProfile).enqueue(object : Callback<Envelope> {
//                    override fun onResponse(call: Call<Envelope>, response: Response<Envelope>) {
//                        if (response.isSuccessful) {
//                            val envelope = response.body()
//                            val alumnoResultJson: String? = envelope?.body?.getAlumnoAcademicoWithLineamientoResponse?.getAlumnoAcademicoWithLineamientoResult
//
//                            // Deserializa la cadena JSON a AlumnoAcademicoResult
//                            val json = Json { ignoreUnknownKeys = true }
//                            val alumnoAcademicoResult: Attributes? = alumnoResultJson?.let { json.decodeFromString(it) }
//
//                            Log.w("exito", "se obtuvo el perfil: ${alumnoAcademicoResult}")
//
//                            getProfile(context, navController,viewModel)
//
//
//                        } else {
//                            showError(context, "Credenciales invalidas")
//                        }
//                    }
//                    override fun onFailure(call: Call<Envelope>, t: Throwable) {
//                        t.printStackTrace()
//                        showError(context, "Error en la solicitud del perfil académico")
//                    }
//                })
//            } else {
//                showError(context, "Error en la autenticación. Código de respuesta: ${response.code()}")
//            }
//        }
//
//        override fun onFailure(call: Call<ResponseBody>, t: Throwable){
//            t.printStackTrace()
//            showError(context, "Error en la solicitud")
//        }
//    })
//}
//
//private fun getProfile(context: Context, navController: NavController, viewModel: ProfileViewModel) {
//    val service = RetrofitClient(context).retrofitService
//    val bodyProfile = profileRequestBody()
//    service.getAcademicProfile(bodyProfile).enqueue(object : Callback<Envelope> {
//        override fun onResponse(call: Call<Envelope>, response: Response<Envelope>) {
//            if (response.isSuccessful) {
//                val envelope = response.body()
//                val alumnoResultJson: String? = envelope?.body?.getAlumnoAcademicoWithLineamientoResponse?.getAlumnoAcademicoWithLineamientoResult
//
//                val json = Json { ignoreUnknownKeys = true }
//                val alumnoAcademicoResult: Attributes? = alumnoResultJson?.let { json.decodeFromString(it) }
//
//                Log.w("Exito", "Se obtuvo el perfil 2: ${alumnoAcademicoResult}")
//                val alumnoAcademicoResultJson = Json.encodeToString(alumnoAcademicoResult)
//
//                val addCookiesInterceptor = AddCookiesInterceptor(context)
//                //addCookiesInterceptor.clearCookies()
//
//                viewModel.attributes=alumnoAcademicoResult
//                navController.navigate("profile")
//            } else {
//                showError(context, "Error al obtener el perfil académico. Código de respuesta: ${response.code()}")
//            }
//        }
//        override fun onFailure(call: Call<Envelope>, t: Throwable) {
//            t.printStackTrace()
//            showError(context, "Error en la solicitud del perfil académico")
//        }
//    })
//}

fun showError(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}
