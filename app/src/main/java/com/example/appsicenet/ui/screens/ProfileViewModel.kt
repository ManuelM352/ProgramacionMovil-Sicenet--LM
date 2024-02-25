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
import com.example.appsicenet.models.CalificacionesFinales

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

    var sicenetUiState: SicenetUiState by mutableStateOf(SicenetUiState.Loading)
        private set

    init {
        getProfile()
        getCalificacionesFinales()
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
