package com.example.recordnotebook.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Build
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.data.storage.preferences.SharedPreferencesStorage

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

fun Context.copyToClipboard(text: CharSequence) {
    val clipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
    // When setting the clip board text.
    clipboardManager.setPrimaryClip(ClipData.newPlainText("", text))
    // Only show a toast for Android 12 and lower.
    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
        Toast.makeText(this, "Copied ", Toast.LENGTH_SHORT).show()
    }
}