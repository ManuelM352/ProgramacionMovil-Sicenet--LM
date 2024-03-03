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
import com.example.appsicenet.SicenetApplication
import com.example.appsicenet.data.SicenetRepository
import com.example.appsicenet.data.database.LocalDataSource

import com.example.appsicenet.models.Attributes
import com.example.appsicenet.models.CalificacionUnidades
import com.example.appsicenet.models.CalificacionesFinales
import com.example.appsicenet.models.CargaAcademica
import com.example.appsicenet.models.Kardex
import com.example.appsicenet.models.LoginResult
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
    private val localDataSource: LocalDataSource
): ViewModel() {

    var accesoLoginResult: LoginResult? = null

    var attributes: Attributes? = null

    var calificacionesFinales: List<CalificacionesFinales>? = null

    var calificacionesUnidades: List<CalificacionUnidades>? = null

    var cargaAcademica: List<CargaAcademica>? = null

    var kardex: Kardex? = null

    var matricula : String = ""
    var contrasenia : String = ""

    var sicenetUiState: SicenetUiState by mutableStateOf(SicenetUiState.Loading)
        private set

    init {
//        getProfile()
//        getCalificacionesFinales()
//        getCalificacionesUnidades()
//        getKardex()
//        getCargaAcademica()
    }

    fun Login() {
        viewModelScope.launch {
            sicenetUiState = SicenetUiState.Loading
            sicenetUiState = try {
                val listResult = withContext(Dispatchers.IO){
                    sicenetRepository.getLoginResult(matricula,contrasenia)
                }
                accesoLoginResult = listResult
                SicenetUiState.Success
            } catch (e: IOException) {
                SicenetUiState.Error
            } catch (e: HttpException) {
                SicenetUiState.Error
            }
        }
    }

    fun performLoginAndFetchAcademicProfile() {
        viewModelScope.launch {
            sicenetUiState = SicenetUiState.Loading

            try {
                val loginResult = withContext(Dispatchers.IO) {
                    sicenetRepository.getLoginResult(matricula,contrasenia)
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



    fun getProfile() {
        viewModelScope.launch {
            sicenetUiState = SicenetUiState.Loading
            sicenetUiState = try {
                val listResult = withContext(Dispatchers.IO){
                    sicenetRepository.getAcademicProfile()
                }
                attributes = listResult

                SicenetUiState.Success
            } catch (e: IOException) {
                SicenetUiState.Error
            } catch (e: HttpException) {
                SicenetUiState.Error
            }
        }
    }

    fun getCalificacionesFinales() {
        viewModelScope.launch {
            sicenetUiState = SicenetUiState.Loading
            sicenetUiState = try {
                val listResult = withContext(Dispatchers.IO) {
                    val calificaciones = sicenetRepository.getCalificacionesFinales()
                    localDataSource.insertCalificaciones(calificaciones)
                    calificaciones
                }
                calificacionesFinales = listResult

                SicenetUiState.Success

            } catch (e: IOException) {
                // Si ocurre un error, intenta obtener los datos de la base de datos local
                calificacionesFinales = localDataSource.getAllCalificaciones()
                SicenetUiState.Error
            } catch (e: HttpException) {
                calificacionesFinales = localDataSource.getAllCalificaciones()
                SicenetUiState.Error
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
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as SicenetApplication)
                val sicenetRepository = application.container.SicenetRepository
                val localDataSource = application.container.localDataSource
                ProfileViewModel(sicenetRepository = sicenetRepository, localDataSource = localDataSource)
            }

        }
    }
}


