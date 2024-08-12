package com.example.weatherstation.response

import com.google.gson.annotations.SerializedName

data class HumidityItem(

	@field:SerializedName("value")
	val value: String,

	@field:SerializedName("ts")
	val ts: Long
)