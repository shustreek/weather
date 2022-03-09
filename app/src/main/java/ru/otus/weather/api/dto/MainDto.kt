package ru.otus.weather.api.dto

import com.google.gson.annotations.SerializedName

data class MainDto (
	@SerializedName("temp") val temp : Double,
	@SerializedName("feels_like") val feelsLike : Double,
	@SerializedName("temp_min") val tempMin : Double,
	@SerializedName("temp_max") val tempMax : Double,
	@SerializedName("pressure") val pressure : Int,
	@SerializedName("humidity") val humidity : Int
)