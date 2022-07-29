package com.mariomanzano.domain

sealed interface Error {
    class Server(val code: Int) : Error
    object Connectivity : Error
    object NoData : Error
    class ObtainingHtml(val message: String) : Error
    class Unknown(val message: String) : Error
}