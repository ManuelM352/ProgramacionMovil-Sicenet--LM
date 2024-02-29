package com.example.appsicenet.ui.screens

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

class ProfileViewModel(private val sicenetRepository: SicenetRepository): ViewModel() {

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
                // Realizar el login
                val loginResult = withContext(Dispatchers.IO) {
                    sicenetRepository.getLoginResult(matricula,contrasenia)
                }

                // Actualizar el estado o manejar el resultado del login según sea necesario
                accesoLoginResult = loginResult

                // Si el login fue exitoso, proceder a obtener el perfil académico
                if (loginResult is LoginResult) {
                    val academicProfileResult = withContext(Dispatchers.IO) {
                        sicenetRepository.getAcademicProfile()
                    }

                    // Actualizar el estado o manejar el resultado del perfil académico según sea necesario
                    attributes = academicProfileResult
                    sicenetUiState = SicenetUiState.Success
                } else {
                    // Manejar el caso en que el login no fue exitoso
                    sicenetUiState = SicenetUiState.Error
                }
            } catch (e: IOException) {
                // Manejar errores de red u otros
                sicenetUiState = SicenetUiState.Error
            } catch (e: HttpException) {
                // Manejar errores HTTP
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

    private fun getCalificacionesFinales() {
        viewModelScope.launch {
            sicenetUiState = SicenetUiState.Loading
            sicenetUiState = try {
                val listResult = withContext(Dispatchers.IO){
                    sicenetRepository.getCalificacionesFinales()
                }
                calificacionesFinales = listResult

                SicenetUiState.Success

            } catch (e: IOException) {
                SicenetUiState.Error
            } catch (e: HttpException) {
                SicenetUiState.Error
            }
        }
    }

    private fun getCalificacionesUnidades() {
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

    private fun getKardex() {
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

    private fun getCargaAcademica() {
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

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as SicenetApplication)
                val sicenetRepository = application.container.SicenetRepository
                ProfileViewModel(sicenetRepository = sicenetRepository)
            }
        }
    }
}
