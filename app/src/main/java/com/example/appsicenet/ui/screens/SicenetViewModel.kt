package com.example.appsicenet.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.appsicenet.SicenetApplication
import com.example.appsicenet.data.SicenetRepository
import com.example.appsicenet.data.database.LocalDataSource

import com.example.appsicenet.models.Login
import com.example.appsicenet.models.CalificacionUnidades
import com.example.appsicenet.models.CalificacionesFinales
import com.example.appsicenet.models.CargaAcademica
import com.example.appsicenet.models.Kardex
import com.example.appsicenet.models.LoginResult
import com.example.appsicenet.workers.CalfFinalesSyncWorker
import com.example.appsicenet.workers.CalfUnidadesWorker
import com.example.appsicenet.workers.CargaAcademicaWorker
import com.example.appsicenet.workers.LoginWorker
import com.example.appsicenet.workers.WorkerState
//import com.example.appsicenet.workers.SyncDataWorker
import kotlinx.coroutines.Dispatchers

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
) : ViewModel() {

    var accesoLoginResult: LoginResult? = null

    var attributes: Login? = null

    var calificacionesFinales: List<CalificacionesFinales>? = null

    var calificacionesUnidades: List<CalificacionUnidades>? = null

    var cargaAcademica: List<CargaAcademica>? = null

    var kardex: Kardex? = null

    var matricula: String? = null
    var contrasenia: String? = null

    // LiveData para representar el estado de cada Worker
    private val _loginWorkerState = MutableLiveData<WorkerState>()
    val loginWorkerState: LiveData<WorkerState> = _loginWorkerState

    private val _calfFinalesSyncWorkerState = MutableLiveData<WorkerState>()
    val calfFinalesSyncWorkerState: LiveData<WorkerState> = _calfFinalesSyncWorkerState
    var sicenetUiState: SicenetUiState by mutableStateOf(SicenetUiState.Loading)
        private set


    fun performLoginAndFetchAcademicProfile(navController: NavController) {
        viewModelScope.launch {
            sicenetUiState = SicenetUiState.Loading
            try {
                // Verificar si hay credenciales almacenadas localmente
                val storedCredentials = localDataSource.getCredentials()

                // Si hay credenciales almacenadas y coinciden con las ingresadas por el usuario
                if (storedCredentials != null && storedCredentials.matricula == matricula &&
                    storedCredentials.contrasenia == contrasenia) {
                    // Obtener datos del perfil desde la base de datos local

                    // Obtener datos del perfil desde la base de datos local
                    val perfil = localDataSource.getAllPerfil()
                    attributes = perfil?.let {
                        Login(
                            especialidad = it.especialidad,
                            carrera = it.carrera,
                            nombre = it.nombre,
                            matricula = it.matricula
                        )
                    }

                    // Navegar a la pantalla de perfil
                    navController.navigate("profile")

                    // Indicar éxito en el estado de la UI
                    sicenetUiState = SicenetUiState.Success
                } else{
                    // Sincronizar datos utilizando WorkManager
                    syncDataWithWorkManager(matricula ?: "", contrasenia ?: "")

                    // Esperar a que se complete la sincronización de datos
                    val loginResult = withContext(Dispatchers.IO) {
                        sicenetRepository.getLoginResult(matricula ?: "", contrasenia ?: "")
                    }

                    accesoLoginResult = loginResult

                    if (loginResult is LoginResult) {
                        val academicProfileResult = withContext(Dispatchers.IO) {
                            sicenetRepository.getAcademicProfile()
                        }
                        attributes = academicProfileResult

                        sicenetUiState = SicenetUiState.Success

                        // Navegar a la pantalla de perfil
                        navController.navigate("profile")
                    } else {
                        sicenetUiState = SicenetUiState.Error
                    }
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


    // Método para sincronizar datos utilizando WorkManager
    fun syncDataWithWorkManager(matricula: String, contrasenia: String) {
        // Configurar el trabajo para autenticación y obtención de perfil
        val authAndProfileRequest = OneTimeWorkRequestBuilder<LoginWorker>()
            .setInputData(workDataOf("matricula" to matricula, "contrasenia" to contrasenia))
            .setConstraints(LoginWorker.constraints) // Agregar restricciones de red
            .build()


        // Configurar el trabajo para la sincronización de calificaciones finales
        val dataSyncRequest = OneTimeWorkRequestBuilder<CalfFinalesSyncWorker>()
            .setInputData(workDataOf("dataType" to "calfFinales"))
            .setConstraints(CalfFinalesSyncWorker.constraints) // Agregar restricciones de red
            .build()

        // Observar el estado del trabajo de autenticación y perfil
        WorkManager.getInstance().getWorkInfoByIdLiveData(authAndProfileRequest.id)
            .observeForever { workInfo ->
                val state = when (workInfo.state) {
                    WorkInfo.State.ENQUEUED -> WorkerState.ENQUEUED
                    WorkInfo.State.RUNNING -> WorkerState.RUNNING
                    WorkInfo.State.SUCCEEDED -> WorkerState.SUCCESS
                    WorkInfo.State.FAILED -> WorkerState.FAILED
                    else -> WorkerState.UNKNOWN
                }
                _loginWorkerState.postValue(state)
            }


        // Observar el estado del trabajo de sincronización de calificaciones finales
        WorkManager.getInstance().getWorkInfoByIdLiveData(dataSyncRequest.id)
            .observeForever { workInfo ->
                val state = when (workInfo.state) {
                    WorkInfo.State.ENQUEUED -> WorkerState.ENQUEUED
                    WorkInfo.State.RUNNING -> WorkerState.RUNNING
                    WorkInfo.State.SUCCEEDED -> WorkerState.SUCCESS
                    WorkInfo.State.FAILED -> WorkerState.FAILED
                    else -> WorkerState.UNKNOWN
                }
                _calfFinalesSyncWorkerState.postValue(state)
            }


        val calfUnidadSyncRequest = OneTimeWorkRequestBuilder<CalfUnidadesWorker>()
            .setInputData(workDataOf("dataType" to "calfUnidades"))
            .build()

        val cargaAcademicaSyncRequest = OneTimeWorkRequestBuilder<CargaAcademicaWorker>()
            .setInputData(workDataOf("dataType" to "cargaAcademica"))
            .build()

        // Encadenar los trabajos para asegurar su ejecución en orden
        WorkManager.getInstance()
            .beginWith(authAndProfileRequest)
            .then(dataSyncRequest)
            //.then(calfUnidadSyncRequest)
            //.then(cargaAcademicaSyncRequest)
            .enqueue()
    }

// En la clase ProfileViewModel, agrega funciones para guardar y obtener las credenciales almacenadas localmente


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
