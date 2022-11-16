package com.example.recordnotebook.utils

import android.content.Context
import android.content.res.Configuration
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.data.storage.preferences.SharedPreferencesStorage
import com.example.recordnotebook.MainActivity

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun setNightMode(mode: Int) {
    when (mode) {
        SharedPreferencesStorage.NIGHT_MODE_OFF -> {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        SharedPreferencesStorage.NIGHT_MODE_ON -> {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        SharedPreferencesStorage.NIGHT_MODE_SYSTEM -> {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }
}