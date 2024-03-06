package com.example.appsicenet.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.appsicenet.SicenetApplication
import com.example.appsicenet.data.SicenetRepository
import com.example.appsicenet.data.database.LocalDataSource

import com.example.appsicenet.models.Attributes
import com.example.appsicenet.models.CalificacionUnidades
import com.example.appsicenet.models.CalificacionesFinales
import com.example.appsicenet.models.CargaAcademica
import com.example.appsicenet.models.Kardex
import com.example.appsicenet.models.LoginResult
import com.example.appsicenet.workers.DataSyncWorker
import com.example.appsicenet.workers.FetchDataWorker
//import com.example.appsicenet.workers.SyncDataWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

sealed interface SicenetUiState {
    object Success: SicenetUiState
    object Error : SicenetUiState
    object Loading : SicenetUiState


}

class ProfileViewModel(
    private val sicenetRepository: SicenetRepository,
    private val localDataSource: LocalDataSource,
    private val workManager: WorkManager
): ViewModel() {

    var accesoLoginResult: LoginResult? = null

    var attributes: Attributes? = null

    var calificacionesFinales: List<CalificacionesFinales>? = null

    var calificacionesUnidades: List<CalificacionUnidades>? = null

    var cargaAcademica: List<CargaAcademica>? = null

    var kardex: Kardex? = null

    var matricula: String? = null
    var contrasenia: String? = null

    var sicenetUiState: SicenetUiState by mutableStateOf(SicenetUiState.Loading)
        private set


    fun performLoginAndFetchAcademicProfile() {
        viewModelScope.launch {
            sicenetUiState = SicenetUiState.Loading
            try {
                // Sincronizar datos utilizando WorkManager
                syncDataWithWorkManager(matricula ?: "", contrasenia ?: "")

                // Esperar a que se complete la sincronización de datos
                val loginResult = withContext(Dispatchers.IO) {
                    sicenetRepository.getLoginResult(matricula ?: "", contrasenia ?: "")
                }

                accesoLoginResult = loginResult
                Log.d("Exito", "result${accesoLoginResult}}")

                if (loginResult is LoginResult) {
                    val academicProfileResult = withContext(Dispatchers.IO) {
                        sicenetRepository.getAcademicProfile()
                    }
                    attributes = academicProfileResult

                    sicenetUiState = SicenetUiState.Success
                } else {
                    sicenetUiState = SicenetUiState.Error
                }
            } catch (e: IOException) {
                sicenetUiState = SicenetUiState.Error
            } catch (e: HttpException) {
                sicenetUiState = SicenetUiState.Error
            }
        }
    }




    fun getCalificacionesFinales() {
        viewModelScope.launch {
            sicenetUiState = SicenetUiState.Loading
            try {
                // Sincronizar datos utilizando WorkManager
                syncDataWithWorkManager(matricula ?: "", contrasenia ?: "")

                // Esperar a que se complete la sincronización de datos
                val listResult = withContext(Dispatchers.IO) {
                    sicenetRepository.getCalificacionesFinales()
                }

                calificacionesFinales = listResult
                sicenetUiState = SicenetUiState.Success
            } catch (e: IOException) {
                sicenetUiState = SicenetUiState.Error
            } catch (e: HttpException) {
                // Si hay una excepción HTTP, obtener las calificaciones finales de la base de datos local
                calificacionesFinales = localDataSource.getAllCalificaciones()
                sicenetUiState = SicenetUiState.Error
            }
        }
    }



    fun getCalificacionesUnidades() {
        viewModelScope.launch {
            sicenetUiState = SicenetUiState.Loading
            sicenetUiState = try {
                val listResult = withContext(Dispatchers.IO){
                    sicenetRepository.getCalificacionesUnidades()
                }
                calificacionesUnidades = listResult
                SicenetUiState.Success
            } catch (e: IOException) {
                SicenetUiState.Error
            } catch (e: HttpException) {
                SicenetUiState.Error
            }
        }
    }

    fun getKardex() {
        viewModelScope.launch {
            sicenetUiState = SicenetUiState.Loading
            sicenetUiState = try {
                val listResult = withContext(Dispatchers.IO){
                    sicenetRepository.getKardex()
                }
                kardex = listResult
                SicenetUiState.Success
            } catch (e: IOException) {
                SicenetUiState.Error
            } catch (e: HttpException) {
                SicenetUiState.Error
            }
        }
    }

    fun getCargaAcademica() {
        viewModelScope.launch {
            sicenetUiState = SicenetUiState.Loading
            sicenetUiState = try {
                val listResult = withContext(Dispatchers.IO){
                    sicenetRepository.getCargaAcademica()
                }
                cargaAcademica = listResult
                SicenetUiState.Success
            } catch (e: IOException) {
                SicenetUiState.Error
            } catch (e: HttpException) {
                SicenetUiState.Error
            }
        }
    }
    fun saveCalificacionesFinales(calificaciones: List<CalificacionesFinales>) {
        viewModelScope.launch {
            // Verificar si las calificaciones ya existen en la base de datos local
            val existingCalificaciones = localDataSource.getAllCalificaciones()
            if (existingCalificaciones.isEmpty()) {
                // Si no existen, guardar las calificaciones finales en la base de datos local
                localDataSource.insertCalificaciones(calificaciones)
            } else {
                // Si existen, no hacer nada
                Log.d("ProfileViewModel", "Las calificaciones ya existen en la base de datos local")
            }
        }
    }


    fun syncDataWithWorkManager(matricula: String, contrasenia: String) {

        // Configurar el trabajo para autenticación y obtención de perfil
        val authAndProfileRequest = OneTimeWorkRequestBuilder<FetchDataWorker>()
            .setInputData(workDataOf("matricula" to matricula, "contrasenia" to contrasenia))
            .build()

        // Configurar el trabajo para otras funcionalidades
        val dataSyncRequest = OneTimeWorkRequestBuilder<DataSyncWorker>()
            .setInputData(workDataOf("dataType" to "calfFinales"))
            .build()

        // Encadenar los trabajos para asegurar su ejecución en orden
        WorkManager.getInstance()
            .beginWith(authAndProfileRequest)
            .then(dataSyncRequest)
            .enqueue()
    }



    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as SicenetApplication)
                val sicenetRepository = application.container.sicenetRepository
                val localDataSource = application.container.localDataSource
                val workManager = application.workManager // Obtiene la instancia de WorkManager de la aplicación
                ProfileViewModel(sicenetRepository, localDataSource, workManager)
            }
        }
    }
}
