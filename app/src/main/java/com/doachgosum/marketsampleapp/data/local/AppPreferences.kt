package com.doachgosum.marketsampleapp.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class AppPreferences(context: Context): MarketDao {

    private val prefs: SharedPreferences = context.getSharedPreferences(
        APP_PREFERENCE_NAME, Context.MODE_PRIVATE
    )

    private val _favoriteMarketFlow: MutableStateFlow<List<Pair<String, String>>> = MutableStateFlow(emptyList())

    companion object {
        private val gson = Gson()

        private const val APP_PREFERENCE_NAME = "SharedPreferences"

        private const val KEY_FAVORITE_MARKET_CURRENCY_PAIR = "KEY_FAVORITE_MARKET_CURRENCY_PAIR"
    }

    override suspend fun getFavoriteMarket(): List<Pair<String, String>> {
        return prefs.getString(KEY_FAVORITE_MARKET_CURRENCY_PAIR, null)
            ?.let {
                gson.fromJson(it, object : TypeToken<List<Pair<String, String>>>(){}.type)
            }
            ?: emptyList()
    }

    override suspend fun saveFavoriteMarket(currencyPair: Pair<String, String>) {
        updateFavoriteMarket(currencyPair, true)
    }

    override suspend fun deleteFavoriteMarket(currencyPair: Pair<String, String>) {
        updateFavoriteMarket(currencyPair, false)
    }
    
    private suspend fun updateFavoriteMarket(currencyPair: Pair<String, String>, toBeSaved: Boolean) {
        val updatedData = getFavoriteMarket()
            .toMutableSet()
            .also {
                if (toBeSaved) {
                    it.add(currencyPair)
                } else {
                    it.remove(currencyPair)
                }
            }
            .toList()

        prefs.edit {
            putString(KEY_FAVORITE_MARKET_CURRENCY_PAIR, gson.toJson(updatedData))
            commit()
        }

        _favoriteMarketFlow.emit(updatedData)
    }

    override fun getFavoriteMarketFlow(): Flow<List<Pair<String, String>>> {
        return _favoriteMarketFlow
    }


}