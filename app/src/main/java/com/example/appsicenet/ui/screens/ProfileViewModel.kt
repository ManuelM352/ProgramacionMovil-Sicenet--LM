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
import com.example.appsicenet.models.Kardex

import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface SicenetUiState {
    data class Success(val datos: String) : SicenetUiState
    object Error : SicenetUiState
    object Loading : SicenetUiState
}

class ProfileViewModel(private val sicenetRepository: SicenetRepository): ViewModel() {
    var attributes: Attributes? = null

    var calificacionesFinales: List<CalificacionesFinales>? = null

    var calificacionesUnidades: List<CalificacionUnidades>? = null

    var kardex: Kardex? = null
    var sicenetUiState: SicenetUiState by mutableStateOf(SicenetUiState.Loading)
        private set

    init {
        getProfile()
        getCalificacionesFinales()
        getCalificacionesUnidades()
        getKardex()
    }

    fun getProfile() {
        viewModelScope.launch {
            sicenetUiState = SicenetUiState.Loading
            sicenetUiState = try {
                val listResult = sicenetRepository.getAcademicProfile()
                SicenetUiState.Success(
                    "Success: $listResult"
                )
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
                val listResult = sicenetRepository.getCalificacionesFinales()
                SicenetUiState.Success(
                    "Success: $listResult"
                )
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
                val listResult = sicenetRepository.getCalificacionesUnidades()
                SicenetUiState.Success(
                    "Success: $listResult"
                )
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
                val listResult = sicenetRepository.getKardex()
                SicenetUiState.Success(
                    "Success: $listResult"
                )
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
