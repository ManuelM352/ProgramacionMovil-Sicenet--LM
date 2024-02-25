package com.example.appsicenet.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root
@Root(name = "Envelope", strict = false)
data class Envelope @JvmOverloads constructor(
    @field:Element(name = "Body", required = false)
    var body: Body = Body()
)

@Root(name = "Body", strict = false)
data class Body @JvmOverloads constructor(
    @field:Element(name = "getAlumnoAcademicoWithLineamientoResponse", required = false)
    var getAlumnoAcademicoWithLineamientoResponse: GetAlumnoAcademicoWithLineamientoResponse = GetAlumnoAcademicoWithLineamientoResponse()
)

@Root(name = "getAlumnoAcademicoWithLineamientoResponse", strict = false)
data class GetAlumnoAcademicoWithLineamientoResponse @JvmOverloads constructor(
    @field:Element(name = "getAlumnoAcademicoWithLineamientoResult", required = false)
    var getAlumnoAcademicoWithLineamientoResult: String = ""
)

//CALIFICACIONES FINALES
// Clases para la respuesta de calificaciones finales
@Root(name = "Envelope", strict = false)
data class EnvelopeCalf @JvmOverloads constructor(
    @field:Element(name = "Body", required = false)
    var bodyCalf: BodyCalf = BodyCalf()
)

@Root(name = "Body", strict = false)
data class BodyCalf @JvmOverloads constructor(
    @field:Element(name = "getAllCalifFinalByAlumnosResponse", required = false)
    var getAllCalifFinalByAlumnosResponse: GetAllCalifFinalByAlumnosResponse = GetAllCalifFinalByAlumnosResponse()
)

@Root(name = "getAllCalifFinalByAlumnosResponse", strict = false)
data class GetAllCalifFinalByAlumnosResponse @JvmOverloads constructor(
    @field:Element(name = "getAllCalifFinalByAlumnosResult", required = false)
    var getAllCalifFinalByAlumnosResult: String = ""
)





@Root(name = "getCalifUnidadesResponse", strict = false)
data class GetCalifUnidadesResponse @JvmOverloads constructor(
    @field:Element(name = "getCalifUnidadesResponse", required = false)
    var getCalifUnidadesResult: String = ""
)

@Root(name = "getKardexResponse", strict = false)
data class GetAllKardexConPromedioResponse @JvmOverloads constructor(
    @field:Element(name = "getKardexResponse", required = false)
    var getKardexResult: String = ""
)

@Root(name = "getCargaAcademicaResponse", strict = false)
data class GetCargaAcademicaResponse @JvmOverloads constructor(
    @field:Element(name = "getCargaAcademicaResponse", required = false)
    var getCargaAcademicaResult: String = ""
)


@Serializable
data class Attributes(
    val especialidad: String,
    val carrera: String,
    val nombre: String,
    val matricula:String
)


@Serializable
data class CalificacionesFinales(
    val calif: Int,
    val acred: String,
    val grupo: String,
    val materia: String,
    val Observaciones: String
)

@Serializable
data class CalificacionesResponse(
    @SerialName("getCalifFinalResult") val calificaciones: List<CalificacionesFinales>
)


@Serializable
data class CalificacionUnidades(
    @SerialName("Observaciones")
    val observaciones: String,
    @SerialName("C5")
    val c5: String?,
    @SerialName("C4")
    val c4: String?,
    @SerialName("C3")
    val c3: String?,
    @SerialName("C2")
    val c2: String?,
    @SerialName("C1")
    val c1: String?,
    @SerialName("UnidadesActivas")
    val unidadesActivas: String,
    @SerialName("Materia")
    val materia: String,
    @SerialName("Grupo")
    val grupo: String
)

@Serializable
data class CalificacionesUnidadesResponse(
    @SerialName("getCalifUnidadesResult") val calificaciones: List<CalificacionUnidades>
)

@Serializable
data class Kardex(
    @SerialName("S1")
    val semestre1: String?,
    @SerialName("P1")
    val periodo1: String?,
    @SerialName("A1")
    val anio1: String?,
    @SerialName("S2")
    val semestre2: String?,
    @SerialName("P2")
    val periodo2: String?,
    @SerialName("A2")
    val anio2: String?,
    @SerialName("S3")
    val semestre3: String?,
    @SerialName("P3") val
    periodo3: String?,
    @SerialName("A3") val
    anio3: String?,
    @SerialName("ClvMat")
    val claveMateria: String,
    @SerialName("ClvOfiMat")
    val claveOficialMateria: String,
    @SerialName("Materia")
    val materia: String,
    @SerialName("Cdts")
    val creditos: Int,
    @SerialName("Calif")
    val calificacion: Int,
    @SerialName("Acred")
    val acreditacion: String
)

@Serializable
data class Promedio(
    @SerialName("PromedioGral")
    val promedioGeneral: Double,
    @SerialName("CdtsAcum")
    val creditosAcumulados: Int,
    @SerialName("CdtsPlan")
    val creditosPlan: Int,
    @SerialName("MatCursadas")
    val materiasCursadas: Int,
    @SerialName("MatAprobadas")
    val materiasAprobadas: Int,
    @SerialName("AvanceCdts")
    val avanceCreditos: Double
)

@Serializable
data class KardexResponse(
    @SerialName("lstKardex")
    val kardex: List<Kardex>,
    @SerialName("Promedio")
    val promedio: Promedio
)


@Serializable
data class Curso(
    @SerialName("Semipresencial")
    val semipresencial: String,
    @SerialName("Observaciones")
    val observaciones: String,
    @SerialName("Docente")
    val docente: String,
    @SerialName("clvOficial")
    val clvOficial: String,
    @SerialName("Sabado")
    val sabado: String,
    @SerialName("Viernes")
    val viernes: String,
    @SerialName("Jueves")
    val jueves: String,
    @SerialName("Miercoles")
    val miercoles: String,
    @SerialName("Martes")
    val martes: String,
    @SerialName("Lunes")
    val lunes: String,
    @SerialName("EstadoMateria")
    val estadoMateria: String,
    @SerialName("CreditosMateria")
    val creditosMateria: Int,
    @SerialName("Materia")
    val materia: String,
    @SerialName("Grupo")
    val grupo: String,
    @SerialName("ClaveMateria")
    val claveMateria: String,
    @SerialName("ClaveOficialMateria")
    val claveOficialMateria: String
)
@Serializable
data class CargaAcademicaResponse(
    @SerialName("getCargaAcademicaResult") val cursos: List<Curso>
)