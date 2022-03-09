package ru.otus.weather.api.dto

import com.google.gson.annotations.SerializedName

data class CloudsDto (
	@SerializedName("all") val all : Int
)