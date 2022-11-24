package com.example.recordnotebook.utils

import android.content.Context
import com.example.domain.usecases.*
import com.example.recordnotebook.R
//TODO implement resource \ create abstract layer

fun parseResult(result: Result?, context: Context): String? {
    if (result == null) return null

    return parseSignInResult(result, context)
        ?: parseMainResult(result, context)
        ?: parseLoginResult(result, context)
        ?: parseAccountResult(result, context)
}

fun parseSignInResult(result: Result, context: Context): String? {
    val res = when (result) {
        is SavedSuccessResult -> {
            context.getString(R.string.saved_success)
        }
        is LogIsExistResult -> {
            context.getString(R.string.log_is_exist)
        }
        is SavedErrorResult -> {
            context.getString(R.string.saved_error)
        }
        else -> null
    }
    return res
}

fun parseMainResult(result: Result, context: Context): String? {
    val res = when (result) {
        is MainSomethingWentWrongResult -> {
            "Something went wrong"
        }
        is MainDeleteSuccessResult -> {
            "Delete success"
        }
        else -> null
    }
    return res
}

fun parseLoginResult(result: Result, context: Context): String? {
    val res = when (result) {
        is LoginSuccessResult -> {
            "Login Success"
        }
        is LoginSomethingWentWrongResult -> {
//            "Something went wrong"
            result.message
        }
        else -> null
    }
    return res
}

fun parseAccountResult(result: Result, context: Context): String? {
    val res = when (result) {
        is AccountCantFindResult -> {
            "Can't find user"
        }
        is AccountSavedSuccessResult -> {
            "Saved - success"
        }
        is AccountOldLoginResult -> {
            "Can't find old login"
        }
        is AccountSomethingWentWrongExceptionResult -> {
            result.message
        }
        is AccountSomethingWentWrongResult -> {
            "Something went wrong"
        }
        is AccountNameTheSameResult -> {
            "Name the same"
        }
        else -> null
    }
    return res
}
