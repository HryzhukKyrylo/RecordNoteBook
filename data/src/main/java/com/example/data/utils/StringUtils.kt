package com.example.data.utils

import android.util.Base64

private val UTF_TYPE = Charsets.UTF_16
private const val BASE_TYPE = Base64.NO_PADDING


fun String.encodeData(): String {
    val byteArray = this.toByteArray(UTF_TYPE)
    val byteArray1 = Base64.encode(byteArray, BASE_TYPE)
    val encoded = Base64.encodeToString(byteArray1, BASE_TYPE)
    return encoded
}

fun String.decodeData(): String {
    val decoded = Base64.decode(this, BASE_TYPE)
    val decoded1 = Base64.decode(decoded, BASE_TYPE)
    val result = String(decoded1, UTF_TYPE)
    return result
}
