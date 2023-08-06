package com.doachgosum.marketsampleapp.data.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun <T> Gson.fromJson(json: String): T {
    return fromJson(json, object : TypeToken<T>(){}.type)
}