package com.example.data.parser

import android.content.Context
import com.example.data.R
import com.example.domain.parser.ResultParser
import com.example.domain.usecases.*

class ResultParserImpl(private val context: Context) : ResultParser {
    override fun invoke(result: Result?): String? {
        if (result == null) return null

        return parseLogUpResult(result, context)
            ?: parseMainResult(result, context)
            ?: parseLoginResult(result, context)
            ?: parseAccountResult(result, context)
    }

    private fun parseLogUpResult(result: Result, context: Context): String? {
        val res = when (result) {
            is SignUpSavedSuccessResult -> {
                context.getString(R.string.saved_success)
            }
            is SignUpLogIsExistResult -> {
                context.getString(R.string.log_is_exist)
            }
            is SignUpSavedErrorResult -> {
                context.getString(R.string.saved_error)
            }
            is SignUpPasswordIsEmptyResult -> {
                context.getString(R.string.parse_signup_password_empty)
            }
            is SignUpLoginIsEmptyResult -> {
                context.getString(R.string.parse_signup_login_empty)
            }
            else -> null
        }
        return res
    }

    private fun parseMainResult(result: Result, context: Context): String? {
        val res = when (result) {
            is MainSomethingWentWrongResult -> {
                context.getString(R.string.parse_main_something_went_wrong)
            }
            is MainDeleteSuccessResult -> {
                context.getString(R.string.parse_main_delete_success)
            }
            else -> null
        }
        return res
    }

    private fun parseLoginResult(result: Result, context: Context): String? {
        val res = when (result) {
            is LoginSuccessResult -> {
                context.getString(R.string.parse_login_login_success)
            }
            is LoginLoginIsEmptyResult -> {
                context.getString(R.string.parse_login_login_is_empty)
            }
            is LoginPasswordIsEmptyResult -> {
                context.getString(R.string.parse_login_password_is_empty)
            }
            is LoginIsNotRegisteredResult -> {
                context.getString(R.string.parse_login_use_not_registered)
            }
            is LoginLoginWrongResult -> {
                context.getString(R.string.parse_login_login_wrong)
            }
            is LoginPasswordWrongResult -> {
                context.getString(R.string.parse_login_password_wrong)
            }
            is LoginFailResult -> {
                context.getString(R.string.parse_login_login_fail)
            }
            is LoginSomethingWentWrongResult -> {
                result.message
            }
            else -> null
        }
        return res
    }

    private fun parseAccountResult(result: Result, context: Context): String? {
        val res = when (result) {
            is AccountCantFindResult -> {
                context.getString(R.string.parse_account_cant_find_user)
            }
            is AccountSavedSuccessResult -> {
                context.getString(R.string.parse_account_saved_success)
            }
            is AccountOldLoginResult -> {
                context.getString(R.string.parse_account_cant_find_old_login)
            }
            is AccountSomethingWentWrongExceptionResult -> {
                result.message
            }
            is AccountSomethingWentWrongResult -> {
                context.getString(R.string.parse_account_something_went_wrong)
            }
            is AccountNameTheSameResult -> {
                context.getString(R.string.parse_account_name_the_same)
            }
            is AccountOldNameIsEmptyResult -> {
                context.getString(R.string.parse_account_old_name_is_empty)
            }
            is AccountOldNameIsNullResult -> {
                context.getString(R.string.parse_account_old_name_null)
            }
            is AccountNewNameIsNullResult -> {
                context.getString(R.string.parse_account_new_name_null)
            }
            is AccountNewNameIsEmptyResult -> {
                context.getString(R.string.parse_account_new_name_empty)
            }
            is AccountCantFindOldPasswordResult -> {
                context.getString(R.string.parse_account_cant_find_old_password)
            }
            is AccountCurrentPasswordEmptyResult -> {
                context.getString(R.string.parse_account_current_password_empty)
            }
            is AccountNewEmptyPasswordResult -> {
                context.getString(R.string.parse_account_new_password_empty)
            }
            is AccountConfirmEmptyPasswordResult -> {
                context.getString(R.string.parse_account_confirm_password_empty)
            }
            is AccountCurrentPasswordWrongResult -> {
                context.getString(R.string.parse_account_current_password_wrong)
            }
            is AccountNewPasswordMatchesResult -> {
                context.getString(R.string.parse_account_new_password_matches)
            }
            is AccountConfirmPasswordWrongResult -> {
                context.getString(R.string.parse_account_confirm_password_wrong)
            }
            is AccountSavedErrorResult -> {
                context.getString(R.string.parse_account_saved_error)
            }
            else -> null
        }
        return res
    }

}