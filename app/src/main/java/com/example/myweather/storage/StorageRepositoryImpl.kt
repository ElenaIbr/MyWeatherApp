package com.example.myweather.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.myweather.domain.models.WeatherModel
import com.example.myweather.domain.repositories.StorageRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class StorageRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
): StorageRepository {
    private object PreferencesKeys {
        val weather = stringPreferencesKey("WeatherData")
    }

    override suspend fun update(weatherModel: WeatherModel) {
        val gson = Gson()
        val hashMapString = gson.toJson(weatherModel)
        dataStore.edit {
            it[PreferencesKeys.weather] = hashMapString
        }
    }

    override suspend fun get(): Flow<WeatherModel?> = flow {
        try {
            dataStore.data.let { flow ->
                val gson = Gson()
                val storedWeather: String? = flow.first().let { it[PreferencesKeys.weather] }
                val type = object : TypeToken<WeatherModel?>() {}.type
                emit(gson.fromJson(storedWeather, type))
            }
        } catch(e: Exception) {
            emit(null)
        }
    }
}
