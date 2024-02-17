package com.example.appsicenet.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "Envelope")
data class AccessLoginResponse(
    @field:Element(name = "Body")
    var body: SoapBody? = null
)

data class SoapBody(
    @field:Element(name = "accesoLoginResponse", required = false)
    var accesoLoginResponse: AccesoLoginResponse? = null
)

data class AccesoLoginResponse(
    @field:Element(name = "accesoLoginResult", required = false)
    var accesoLoginResult: String? = null
)

@Serializable
data class LoginResult(
    @SerialName
        ("acceso") val access: Boolean,
    @SerialName
        ("estatus") val status: String,
    @SerialName
        ("tipoUsuario") val userType: Int,
    @SerialName
        ("contrasenia") val password: String,
    @SerialName
        ("matricula") val matricula: String
)