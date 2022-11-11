package com.example.domain

sealed interface Response
sealed class IOResponse {
    class Succsess(val message: String?,val data: Any?) : Response
    class Error(val errorMessage: String?) : Response
}
