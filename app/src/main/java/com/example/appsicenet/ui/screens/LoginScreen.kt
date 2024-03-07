package com.example.appsicenet.ui.screens

import android.content.Context
import android.os.Build
import android.util.Log
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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import com.example.appsicenet.data.database.LocalDataSource
import com.example.appsicenet.workers.WorkerState


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LoginScreen(navController: NavController, viewModel: ProfileViewModel) {
    var matricula by remember { mutableStateOf("") }
    var contrasenia by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var saveSession by remember { mutableStateOf(false) } // Aquí se define la variable saveSession
    val context = LocalContext.current
    val localDataSource: LocalDataSource
    val h4TextStyle = androidx.compose.ui.text.TextStyle(
        fontSize = 24.sp, // Tamaño de fuente deseado
        fontWeight = FontWeight.Bold // Peso de la fuente deseado
    )

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
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Checkbox(
                checked = saveSession,
                onCheckedChange = { saveSession = it },
                modifier = Modifier.padding(end = 8.dp),
                colors = CheckboxDefaults.colors(MaterialTheme.colorScheme.primary)
            )
            Text(text = "Guardar sesión")
        }
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = {
                    //authenticate(context,matricula, contrasenia, navController, viewModel)
                    viewModel.matricula = matricula
                    viewModel.contrasenia = contrasenia
                    viewModel.performLoginAndFetchAcademicProfile(navController)
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

            // Observa el estado del Worker de inicio de sesión y muestra un mensaje de error si es necesario
            when (viewModel.loginWorkerState.value) {
                WorkerState.FAILED -> {
                    showError(context, "Error al iniciar sesión. Verifica tus credenciales e intenta nuevamente.")
                }
                else -> {}
            }
        }
    }
}
fun showError(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}
