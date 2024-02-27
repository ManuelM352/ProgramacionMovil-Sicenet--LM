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




//CALIFICACIONES UNIDAD
@Root(name = "Envelope", strict = false)
data class EnvelopeCalfUni @JvmOverloads constructor(
    @field:Element(name = "Body", required = false)
    var bodyCalfUni: BodyCalfUni = BodyCalfUni()
)

@Root(name = "Body", strict = false)
data class BodyCalfUni @JvmOverloads constructor(
    @field:Element(name = "getCalifUnidadesByAlumnoResponse", required = false)
    var getCalifUnidadesByAlumnoResponse: GetCalifUnidadesByAlumnoResponse = GetCalifUnidadesByAlumnoResponse()
)

@Root(name = "getCalifUnidadesByAlumnoResponse", strict = false)
data class GetCalifUnidadesByAlumnoResponse @JvmOverloads constructor(
    @field:Element(name = "getCalifUnidadesByAlumnoResult", required = false)
    var getCalifUnidadesByAlumnoResult: String = ""
)


//KARDEX
@Root(name = "Envelope", strict = false)
data class EnvelopeKardex @JvmOverloads constructor(
    @field:Element(name = "Body", required = false)
    var bodyKardex: BodyKardex = BodyKardex()
)

@Root(name = "Body", strict = false)
data class BodyKardex @JvmOverloads constructor(
    @field:Element(name = "getAllKardexConPromedioByAlumnoResponse", required = false)
    var getAllKardexConPromedioByAlumnoResponse: GetAllKardexConPromedioByAlumnoResponse = GetAllKardexConPromedioByAlumnoResponse()
)

@Root(name = "getAllKardexConPromedioByAlumnoResponse", strict = false)
data class GetAllKardexConPromedioByAlumnoResponse @JvmOverloads constructor(
    @field:Element(name = "getAllKardexConPromedioByAlumnoResult", required = false)
    var getAllKardexConPromedioByAlumnoResult: String = ""
)


//CARGA ACADEMICA
@Root(name = "Envelope", strict = false)
data class EnvelopeCargaAcademica @JvmOverloads constructor(
    @field:Element(name = "Body", required = false)
    var bodyCargaAcademica: BodyCargaAcademica = BodyCargaAcademica()
)

@Root(name = "Body", strict = false)
data class BodyCargaAcademica @JvmOverloads constructor(
    @field:Element(name = "getCargaAcademicaByAlumnoResponse", required = false)
    var getCargaAcademicaByAlumnoResponse: GetCargaAcademicaByAlumnoResponse = GetCargaAcademicaByAlumnoResponse()
)

@Root(name = "getCargaAcademicaByAlumnoResponse", strict = false)
data class GetCargaAcademicaByAlumnoResponse @JvmOverloads constructor(
    @field:Element(name = "getCargaAcademicaByAlumnoResult", required = false)
    var getCargaAcademicaByAlumnoResult: String = ""
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
data class CalificacionUnidades(
    @SerialName("Observaciones")
    val observaciones: String?,
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
data class KardexItem(
    @SerialName("S3")
    val s3: String?,

    @SerialName("P3")
    val p3: String?,

    @SerialName("A3")
    val a3: String?,

    @SerialName("ClvMat")
    val clvMat: String,

    @SerialName("ClvOfiMat")
    val clvOfiMat: String,

    @SerialName("Materia")
    val materia: String,

    @SerialName("Cdts")
    val cdts: Int,

    @SerialName("Calif")
    val calif: Int,

    @SerialName("Acred")
    val acred: String,

    @SerialName("S1")
    val s1: String,

    @SerialName("P1")
    val p1: String,

    @SerialName("A1")
    val a1: String,

    @SerialName("S2")
    val s2: String?,

    @SerialName("P2")
    val p2: String?,

    @SerialName("A2")
    val a2: String?
)

@Serializable
data class Promedio(
    @SerialName("PromedioGral")
    val promedioGral: Double,

    @SerialName("CdtsAcum")
    val cdtsAcum: Int,

    @SerialName("CdtsPlan")
    val cdtsPlan: Int,

    @SerialName("MatCursadas")
    val matCursadas: Int,

    @SerialName("MatAprobadas")
    val matAprobadas: Int,

    @SerialName("AvanceCdts")
    val avanceCdts: Double
)

@Serializable
data class Kardex(
    @SerialName("lstKardex")
    val lstKardex: List<KardexItem>,

    @SerialName("Promedio")
    val promedio:Promedio
)



@Serializable
data class CargaAcademica(
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
    val grupo: String

)
