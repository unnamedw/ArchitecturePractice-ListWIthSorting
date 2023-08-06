package com.doachgosum.marketsampleapp

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        val gson = Gson()
        val set: Set<Pair<String, String>> = setOf("철수" to "영희", "강남" to "강북")
            .also { println(it) }

        val json = gson.toJson(set)
            .also { println(it) }

        gson.fromJson<Set<Pair<String, String>>>(json, object : TypeToken<Set<Pair<String, String>>>(){}.type)
            .also { println(it) }
    }
}