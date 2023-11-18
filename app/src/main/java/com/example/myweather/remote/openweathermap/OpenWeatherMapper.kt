package com.example.myweather.remote.openweathermap

import com.example.myweather.domain.models.WeatherModel
import com.example.myweather.remote.base.RemoteConverter
import javax.inject.Inject

class OpenWeatherMapper @Inject constructor(
) : RemoteConverter<OpenWeatherResponse, WeatherModel> {
    override fun convertFromRemote(remoteModel: OpenWeatherResponse): WeatherModel {
        return WeatherModel(
            lat = remoteModel.lat,
            lon = remoteModel.lon,
            timezone = remoteModel.timezone,
            current = remoteModel.current?.toDomain()
        )
    }
    private fun OpenWeatherResponse.Current.toDomain() =
        WeatherModel.Current(
            temp = this.temp,
            feelsLike = this.feelsLike,
            windSpeed = this.windSpeed,
            weather = this.weather?.map { it.toDomain() }
        )
    private fun OpenWeatherResponse.Current.Weather.toDomain() =
        WeatherModel.Current.Weather(
            id = this.id,
            main = this.main,
            description = this.description,
            icon = this.icon?.getIconUrl()
        )
    private fun String.getIconUrl() =
        "https://openweathermap.org/img/wn/${this}@2x.png"
}
