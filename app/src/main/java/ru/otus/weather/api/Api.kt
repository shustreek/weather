package ru.otus.weather.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.otus.weather.api.dto.CityWeatherResponse

interface Api {

    @GET("weather")
    suspend fun getWeather(@Query("id") id: Long, @Query("units") units: String): CityWeatherResponse
}
