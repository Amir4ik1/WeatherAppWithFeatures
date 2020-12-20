package ru.nondoanything.weatherappwithfeatures.data.response

import com.google.gson.annotations.SerializedName


data class CurrentWeatherResponse(
        @SerializedName("current")
        val CurrentWeatherEntry: CurrentWeatherEntry,
        val location: Location
)