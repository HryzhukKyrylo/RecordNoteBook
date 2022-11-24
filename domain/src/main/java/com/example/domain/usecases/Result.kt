package com.example.domain.usecases

interface Result


object SavedSuccessResult : Result

object LogIsExistResult : Result
object SavedErrorResult : Result

object SettingScreenDeleteSuccessResult : Result
object SettingScreenDeleteErrorResult : Result
object SettingScreenSomethingWentWrongResult : Result

object MainDeleteSuccessResult : Result
object MainDeleteErrorResult : Result
object MainSomethingWentWrongResult : Result

object LoginSuccessResult : Result
data class LoginSomethingWentWrongResult(val message:String?) : Result


object AccountCantFindResult : Result
object AccountSavedSuccessResult : Result
object AccountOldLoginResult : Result
object AccountSomethingWentWrongResult : Result
object AccountNameTheSameResult : Result
data class AccountSomethingWentWrongExceptionResult(val message:String?) : Result



