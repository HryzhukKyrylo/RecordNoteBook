package com.example.domain.usecases

interface Result


object SignUpSavedSuccessResult : Result

object SignUpLogIsExistResult : Result
object SignUpSavedErrorResult : Result
object SignUpLoginIsEmptyResult : Result
object SignUpPasswordIsEmptyResult : Result

object SettingScreenDeleteSuccessResult : Result
object SettingScreenDeleteErrorResult : Result
object SettingScreenSomethingWentWrongResult : Result

object MainDeleteSuccessResult : Result
object MainDeleteErrorResult : Result
object MainSomethingWentWrongResult : Result

object LoginSuccessResult : Result
object LoginFailResult : Result
object LoginPasswordWrongResult : Result
object LoginLoginWrongResult : Result
object LoginIsNotRegisteredResult : Result
object LoginLoginIsEmptyResult : Result
object LoginPasswordIsEmptyResult : Result
data class LoginSomethingWentWrongResult(val message: String?) : Result


object AccountCantFindResult : Result
object AccountSavedSuccessResult : Result
object AccountOldLoginResult : Result
object AccountSomethingWentWrongResult : Result
object AccountNameTheSameResult : Result
object AccountOldNameIsEmptyResult : Result
object AccountOldNameIsNullResult : Result
object AccountNewNameIsNullResult : Result
object AccountNewNameIsEmptyResult : Result
object AccountCantFindOldPasswordResult : Result
object AccountCurrentPasswordEmptyResult : Result
object AccountNewEmptyPasswordResult : Result
object AccountConfirmEmptyPasswordResult : Result
object AccountCurrentPasswordWrongResult : Result
object AccountNewPasswordMatchesResult : Result
object AccountConfirmPasswordWrongResult : Result
object AccountSavedErrorResult : Result
data class AccountSomethingWentWrongExceptionResult(val message: String?) : Result



