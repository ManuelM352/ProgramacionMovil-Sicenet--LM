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

@Serializable
data class Attributes(
    val especialidad: String,
    val carrera: String,
    val nombre: String,
    val matricula:String
)