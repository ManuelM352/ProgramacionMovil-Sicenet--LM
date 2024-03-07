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

//LOGIN
@Root(name = "Envelope", strict = false)
data class EnvelopeLogin @JvmOverloads constructor(
    @field:Element(name = "Body", required = false)
    var bodyLogin: BodyLogin = BodyLogin()
)

@Root(name = "Body", strict = false)
data class BodyLogin @JvmOverloads constructor(
    @field:Element(name = "accesoLoginResponse", required = false)
    var accesoLoginResponse: AccesoLoginResponse = AccesoLoginResponse()
)

@Root(name = "accesoLoginResult", strict = false)
data class AccesoLoginResponse @JvmOverloads constructor(
    @field:Element(name = "accesoLoginResult", required = false)
    var accesoLoginResult: String = ""
)


//CALIFICACIONES FINALES
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
data class Login(
    val especialidad: String? = null,
    val carrera: String? = null,
    val nombre: String? = null,
    val matricula:String? = null
)


@Serializable
data class CalificacionesFinales(
    val calif: Int? = null,
    val acred: String? = null,
    val grupo: String? = null,
    val materia: String? = null,
    val Observaciones: String? = null
)


@Serializable
data class CalificacionUnidades(
    @SerialName("Observaciones")
    val observaciones: String? = null,
    @SerialName("C5")
    val c5: String? = null,
    @SerialName("C4")
    val c4: String?= null,
    @SerialName("C3")
    val c3: String?= null,
    @SerialName("C2")
    val c2: String?= null,
    @SerialName("C1")
    val c1: String?= null,
    @SerialName("UnidadesActivas")
    val unidadesActivas: String?= null,
    @SerialName("Materia")
    val materia: String?= null,
    @SerialName("Grupo")
    val grupo: String?= null
)


@Serializable
data class KardexItem(
    @SerialName("S3")
    val s3: String?= null,

    @SerialName("P3")
    val p3: String?= null,

    @SerialName("A3")
    val a3: String?= null,

    @SerialName("ClvMat")
    val clvMat: String?= null,

    @SerialName("ClvOfiMat")
    val clvOfiMat: String?= null,

    @SerialName("Materia")
    val materia: String?= null,

    @SerialName("Cdts")
    val cdts: Int?= null,

    @SerialName("Calif")
    val calif: Int?= null,

    @SerialName("Acred")
    val acred: String?= null,

    @SerialName("S1")
    val s1: String?= null,

    @SerialName("P1")
    val p1: String?= null,

    @SerialName("A1")
    val a1: String?= null,

    @SerialName("S2")
    val s2: String?= null,

    @SerialName("P2")
    val p2: String?= null,

    @SerialName("A2")
    val a2: String? = null
)

@Serializable
data class Promedio(
    @SerialName("PromedioGral")
    val promedioGral: Double? = null,

    @SerialName("CdtsAcum")
    val cdtsAcum: Int? = null,

    @SerialName("CdtsPlan")
    val cdtsPlan: Int? = null,

    @SerialName("MatCursadas")
    val matCursadas: Int? = null,

    @SerialName("MatAprobadas")
    val matAprobadas: Int? = null,

    @SerialName("AvanceCdts")
    val avanceCdts: Double? = null
)

@Serializable
data class Kardex(
    @SerialName("lstKardex")
    val lstKardex: List<KardexItem>? = null,

    @SerialName("Promedio")
    val promedio:Promedio? = null
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

@Serializable
data class LoginResult(
    val acceso: Boolean? = null,
    val status: String? = null,
    val user: Int? = null,
    val contrasenia: String? = null,
    val matricula: String? = null
)


