package ru.otus.weather.api.dto

import com.google.gson.annotations.SerializedName

data class WindDto(
    @SerializedName("speed") val speed: Float,
    @SerializedName("deg") val deg: Float
)