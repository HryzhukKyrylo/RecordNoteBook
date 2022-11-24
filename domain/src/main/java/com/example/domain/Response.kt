package com.example.domain

import com.example.domain.usecases.Result

sealed interface Response
sealed class IOResponse():Response {
    class Success(val message: Result?, val data: Any?) : Response
    class Error(val errorMessage: Result?) : Response
}
