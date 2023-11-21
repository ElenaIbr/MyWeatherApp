package com.example.myweather.storage

import android.content.Context
import android.location.Address
import android.location.Geocoder
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.myweather.domain.models.WeatherModel
import com.example.myweather.domain.repositories.StorageRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.first
import java.io.IOException
import java.util.Locale
import javax.inject.Inject

class StorageRepositoryImpl @Inject constructor(
    private val context: Context,
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

    override suspend fun get(): WeatherModel? {
        return try {
            dataStore.data.let { flow ->
                val gson = Gson()
                val storedWeather: String? = flow.first().let { it[PreferencesKeys.weather] }
                val type = object : TypeToken<WeatherModel?>() {}.type
                gson.fromJson(storedWeather, type)
            }
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getAddress(lat: Double, lon: Double): String? {
        val geocoder = Geocoder(context, Locale.getDefault())
        return try {
            val addresses: List<Address>? = geocoder.getFromLocation(lat, lon, 1)
            val obj: Address = addresses!![0]
            "${obj.thoroughfare}, ${obj.postalCode} ${obj.subAdminArea}, ${obj.countryName}"
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}
