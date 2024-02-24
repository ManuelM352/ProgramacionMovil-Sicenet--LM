package com.example.appsicenet.models

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

@Root(name = "getAllCalifFinalByAlumnosResponse", strict = false)
data class GetAllCalifFinalByAlumnosResponse @JvmOverloads constructor(
    @field:Element(name = "getAllCalifFinalByAlumnosResult", required = false)
    var getAllCalifFinalByAlumnosResult: String = ""
)

@Root(name = "getCalifUnidadesByAlumnoResponse", strict = false)
data class GetCalifUnidadesByAlumnoResponse @JvmOverloads constructor(
    @field:Element(name = "getCalifUnidadesByAlumnoResult", required = false)
    var getCalifUnidadesByAlumnoResult: String = ""
)

@Root(name = "getAllKardexConPromedioByAlumnoResponse", strict = false)
data class GetAllKardexConPromedioByAlumnoResponse @JvmOverloads constructor(
    @field:Element(name = "getAllKardexConPromedioByAlumnoResult", required = false)
    var getAllKardexConPromedioByAlumnoResult: String = ""
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