package com.doachgosum.marketsampleapp.data.local

import android.content.Context
import android.content.SharedPreferences

class AppPreferences(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(
        APP_PREFERENCE_NAME, Context.MODE_PRIVATE
    )

    companion object {
        private const val APP_PREFERENCE_NAME = "SharedPreferences"
    }

}