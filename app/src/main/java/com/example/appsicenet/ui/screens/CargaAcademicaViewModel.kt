package com.example.appsicenet.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.appsicenet.SicenetApplication
import com.example.appsicenet.data.CargaAcademicaRepository
import com.example.appsicenet.data.TableCargaAcademica

class CargaAcademicaViewModel (private val itemsRepository: CargaAcademicaRepository) : ViewModel() {

    /**
     * Holds current item ui state
     */
    var itemUiState by mutableStateOf(CargaAcademicaUiState())
        private set

    /**
     * Updates the [itemUiState] with the value provided in the argument. This method also triggers
     * a validation for input values.
     */
    fun updateUiState(itemDetails: CargaAcademicaDetails) {
        itemUiState =
            CargaAcademicaUiState(itemDetails = itemDetails)
    }

    suspend fun saveItem() {
        itemsRepository.insertItem(itemUiState.itemDetails.toItem())
    }


//    companion object {
//        val Factory: ViewModelProvider.Factory = viewModelFactory {
//            initializer {
//                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as SicenetApplication)
//                val cargaAcademicaRepository = application.container.itemsRepository
//                CargaAcademicaViewModel(itemsRepository = cargaAcademicaRepository)
//            }
//        }
//    }
}

/**
 * Represents Ui State for an Item.
 */
data class CargaAcademicaUiState(
    val itemDetails: CargaAcademicaDetails = CargaAcademicaDetails()
)

data class CargaAcademicaDetails(
    val clvOficial: String = "",
    val semipresencial: String = "",
    val observaciones: String = "",
    val docente: String = "",
    val sabado: String = "",
    val viernes: String = "",
    val jueves: String = "",
    val miercoles: String = "",
    val martes: String = "",
    val lunes: String = "",
    val estadoMateria: String = "",
    val creditosMateria: Int = 0,
    val materia: String = "",
    val grupo: String = ""
)

/**
 * Extension function to convert [ItemDetails] to [Item]. If the value of [ItemDetails.price] is
 * not a valid [Double], then the price will be set to 0.0. Similarly if the value of
 * [ItemDetails.quantity] is not a valid [Int], then the quantity will be set to 0
 */
fun CargaAcademicaDetails.toItem(): TableCargaAcademica = TableCargaAcademica(
    clvOficial = clvOficial,
    semipresencial = semipresencial,
    observaciones = observaciones,
    docente = docente,
    sabado = sabado,
    viernes = viernes,
    jueves = jueves,
    miercoles = miercoles,
    martes = martes,
    lunes = lunes,
    estadoMateria = estadoMateria,
    creditosMateria = creditosMateria.toInt()?:0,
    materia = materia,
    grupo = grupo
)

/**
 * Extension function to convert [Item] to [ItemUiState]
 */
fun TableCargaAcademica.toItemUiState(): CargaAcademicaUiState = CargaAcademicaUiState(
    itemDetails = this.toItemDetails()
)

/**
 * Extension function to convert [Item] to [ItemDetails]
 */
fun TableCargaAcademica.toItemDetails(): CargaAcademicaDetails = CargaAcademicaDetails(
    clvOficial = clvOficial,
    semipresencial = semipresencial,
    observaciones = observaciones,
    docente = docente,
    sabado = sabado,
    viernes = viernes,
    jueves = jueves,
    miercoles = miercoles,
    martes = martes,
    lunes = lunes,
    estadoMateria = estadoMateria,
    creditosMateria = creditosMateria,
    materia = materia,
    grupo = grupo
)
