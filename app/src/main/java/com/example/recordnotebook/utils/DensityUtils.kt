package com.example.recordnotebook.utils

import android.content.res.Resources

val Int.dp: Float
    get() = Resources.getSystem().displayMetrics.density * this