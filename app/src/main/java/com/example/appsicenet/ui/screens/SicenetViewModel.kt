package com.example.appsicenet.ui.screens

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
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
import com.example.appsicenet.models.KardexItem
import com.example.appsicenet.models.LoginResult
import com.example.appsicenet.models.Promedio
import com.example.appsicenet.workers.CalfFinalesSyncWorker
import com.example.appsicenet.workers.CalfUnidadesWorker
import com.example.appsicenet.workers.CargaAcademicaWorker
import com.example.appsicenet.workers.KardexWorker
import com.example.appsicenet.workers.LoginWorker
import com.example.appsicenet.workers.PromedioWorker
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

    private val _calfUnidadesSyncWorkerState = MutableLiveData<WorkerState>()
    val calfUnidadesSyncWorkerState: LiveData<WorkerState> = _calfUnidadesSyncWorkerState

    private val _cargaAcademicaSyncWorkerState = MutableLiveData<WorkerState>()
    val cargaAcademicaSyncWorkerState: LiveData<WorkerState> = _cargaAcademicaSyncWorkerState

    private val _kardexSyncWorkerState = MutableLiveData<WorkerState>()
    val kardexSyncWorkerState: LiveData<WorkerState> = _kardexSyncWorkerState

    private val _promedioSyncWorkerState = MutableLiveData<WorkerState>()
    val promedioSyncWorkerState: LiveData<WorkerState> = _promedioSyncWorkerState

    var sicenetUiState: SicenetUiState by mutableStateOf(SicenetUiState.Loading)
        private set


    fun performLoginAndFetchAcademicProfile(navController: NavController, context: Context) {
        viewModelScope.launch {
            sicenetUiState = SicenetUiState.Loading
            try {
                // Verificar si hay credenciales almacenadas localmente
                val storedCredentials = localDataSource.getCredentials()
                // Si hay credenciales almacenadas y coinciden con las ingresadas por el usuario
                // Si hay credenciales almacenadas y coinciden con las ingresadas por el usuario
                if (storedCredentials != null && storedCredentials.matricula == matricula &&
                    storedCredentials.contrasenia == contrasenia && !conexion(context)) {
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

                    // Obtener datos de calificaciones finales desde la base de datos local
                    val calfFinal = localDataSource.getAllCalificaciones()
                    calificacionesFinales = calfFinal?.map {
                        CalificacionesFinales(
                            calif = it.calif,
                            acred = it.acred,
                            grupo = it.grupo,
                            materia = it.materia,
                            Observaciones = it.Observaciones
                        )
                    }

                    // Obtener datos de calificaciones finales desde la base de datos local
                    val calfunidad = localDataSource.getAllCalificacionesUnidad()
                    calificacionesUnidades = calfunidad?.map {
                        CalificacionUnidades(
                            observaciones = it.observaciones,
                            c5 = it.c5,
                            c4 = it.c4,
                            c3 = it.c3,
                            c2 = it.c2,
                            c1 = it.c1,
                            unidadesActivas = it.unidadesActivas,
                            materia = it.materia,
                            grupo = it.grupo
                        )
                    }

                    // Obtener datos de calificaciones finales desde la base de datos local
                    val cargaAcademicaEntities = localDataSource.getAllCargaAcademica()
                    cargaAcademica = cargaAcademicaEntities?.map {
                        CargaAcademica(
                            semipresencial = it.semipresencial,
                            observaciones = it.observaciones,
                            docente = it.docente,
                            clvOficial = it.clvOficial,
                            sabado = it.sabado,
                            viernes = it.viernes,
                            jueves = it.jueves,
                            miercoles = it.miercoles,
                            martes = it.martes,
                            lunes = it.lunes,
                            estadoMateria = it.estadoMateria,
                            creditosMateria = it.creditosMateria,
                            materia = it.materia,
                            grupo = it.grupo
                        )
                    }

                    // Obtener datos de kardex desde la base de datos local
                    val kardexEntities = localDataSource.getAllkardex()
                    val promedioEntities = localDataSource.getAllPromedio()
                    kardex = Kardex(
                        lstKardex = kardexEntities?.map {
                            KardexItem(
                                s3 = it.s3,
                                p3 = it.p3,
                                a3 = it.a3,
                                clvMat = it.clvMat,
                                clvOfiMat = it.clvOfiMat,
                                materia = it.materia,
                                cdts = it.cdts,
                                calif = it.calif,
                                acred = it.acred,
                                s1 = it.s1,
                                p1 = it.p1,
                                a1 = it.a1,
                                s2 = it.s2,
                                p2 = it.p2,
                                a2 = it.a2,
                                fecha = it.fecha
                            )
                        },
                        promedio = promedioEntities?.let {
                            Promedio(
                                promedioGral = it.promedioGral,
                                cdtsAcum = it.cdtsAcum,
                                cdtsPlan = it.cdtsPlan,
                                matCursadas = it.matCursadas,
                                matAprobadas = it.matAprobadas,
                                avanceCdts = it.avanceCdts,
                                fecha = it.fecha
                            )
                        }
                    )

                    // Navegar a la pantalla de perfil
                    navController.navigate("profile")

                    // Indicar éxito en el estado de la UI
                    sicenetUiState = SicenetUiState.Success
                } else{

                    // Esperar a que se complete la sincronización de datos
                    val loginResult = withContext(Dispatchers.IO) {
                        sicenetRepository.getLoginResult(matricula ?: "", contrasenia ?: "")
                    }
                    syncDataWithWorkManager(matricula ?: "", contrasenia ?: "")
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


    fun conexion(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            return networkCapabilities != null &&
                    (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
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
            try {
                syncDataWithWorkManager(matricula ?: "", contrasenia ?: "")
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


        // Configurar el trabajo para la sincronización de calificaciones por unidad
        val califUnidades = OneTimeWorkRequestBuilder<CalfUnidadesWorker>()
            .setInputData(workDataOf("dataType" to "calfUni"))
            .setConstraints(CalfUnidadesWorker.constraints) // Agregar restricciones de red
            .build()

        // Configurar el trabajo para la sincronización de CargaAcademicaWorker
        val cargaAcademica = OneTimeWorkRequestBuilder<CargaAcademicaWorker>()
            .setInputData(workDataOf("dataType" to "cargaAcademica"))
            .setConstraints(CargaAcademicaWorker.constraints) // Agregar restricciones de red
            .build()

        // Configurar el trabajo para la sincronización de KardexWorker
        val kardex = OneTimeWorkRequestBuilder<KardexWorker>()
            .setInputData(workDataOf("dataType" to "kardex"))
            .setConstraints(KardexWorker.constraints) // Agregar restricciones de red
            .build()

        // Configurar el trabajo para la sincronización de PromedioWorker
        val promedio = OneTimeWorkRequestBuilder<PromedioWorker>()
            .setInputData(workDataOf("dataType" to "promedio"))
            .setConstraints(PromedioWorker.constraints) // Agregar restricciones de red
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

        // Observar el estado del trabajo de califUnidades
        WorkManager.getInstance().getWorkInfoByIdLiveData(califUnidades.id)
            .observeForever { workInfo ->
                val state = when (workInfo.state) {
                    WorkInfo.State.ENQUEUED -> WorkerState.ENQUEUED
                    WorkInfo.State.RUNNING -> WorkerState.RUNNING
                    WorkInfo.State.SUCCEEDED -> WorkerState.SUCCESS
                    WorkInfo.State.FAILED -> WorkerState.FAILED
                    else -> WorkerState.UNKNOWN
                }
                _calfUnidadesSyncWorkerState.postValue(state)
            }


        // Observar el estado del trabajo de sincronización de cargaAcademica
        WorkManager.getInstance().getWorkInfoByIdLiveData(cargaAcademica.id)
            .observeForever { workInfo ->
                val state = when (workInfo.state) {
                    WorkInfo.State.ENQUEUED -> WorkerState.ENQUEUED
                    WorkInfo.State.RUNNING -> WorkerState.RUNNING
                    WorkInfo.State.SUCCEEDED -> WorkerState.SUCCESS
                    WorkInfo.State.FAILED -> WorkerState.FAILED
                    else -> WorkerState.UNKNOWN
                }
                _cargaAcademicaSyncWorkerState.postValue(state)
            }

        // Observar el estado del trabajo de sincronización de kardex
        WorkManager.getInstance().getWorkInfoByIdLiveData(kardex.id)
            .observeForever { workInfo ->
                val state = when (workInfo.state) {
                    WorkInfo.State.ENQUEUED -> WorkerState.ENQUEUED
                    WorkInfo.State.RUNNING -> WorkerState.RUNNING
                    WorkInfo.State.SUCCEEDED -> WorkerState.SUCCESS
                    WorkInfo.State.FAILED -> WorkerState.FAILED
                    else -> WorkerState.UNKNOWN
                }
                _kardexSyncWorkerState.postValue(state)
            }

        // Observar el estado del trabajo de sincronización de promedio
        WorkManager.getInstance().getWorkInfoByIdLiveData(promedio.id)
            .observeForever { workInfo ->
                val state = when (workInfo.state) {
                    WorkInfo.State.ENQUEUED -> WorkerState.ENQUEUED
                    WorkInfo.State.RUNNING -> WorkerState.RUNNING
                    WorkInfo.State.SUCCEEDED -> WorkerState.SUCCESS
                    WorkInfo.State.FAILED -> WorkerState.FAILED
                    else -> WorkerState.UNKNOWN
                }
                _promedioSyncWorkerState.postValue(state)
            }

        // Encadenar los trabajos para asegurar su ejecución en orden
        WorkManager.getInstance()
            .beginWith(authAndProfileRequest)
            .then(cargaAcademica)
            .enqueue()


        // Encadenar los trabajos para asegurar su ejecución en orden
        WorkManager.getInstance()
            .beginWith(dataSyncRequest)
            .then(califUnidades)
            .enqueue()

        // Encadenar los trabajos para asegurar su ejecución en orden
        WorkManager.getInstance()
            .beginWith(kardex)
            .then(promedio)
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
