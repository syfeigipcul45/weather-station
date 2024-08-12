package com.example.weatherstation.response

import com.google.gson.annotations.SerializedName

data class TelemetryResponse(

	@field:SerializedName("light_sensor")
	val lightSensor: List<LightSensorItem>,

	@field:SerializedName("rain_hour")
	val rainHour: List<RainHourItem>,

	@field:SerializedName("PM2.5")
	val pM2_5: List<PM2_5Item>,

	@field:SerializedName("rain_time")
	val rainTime: List<RainTimeItem>,

	@field:SerializedName("rain_raw")
	val rainRaw: List<RainRawItem>,

	@field:SerializedName("temperature")
	val temperature: List<TemperatureItem>,

	@field:SerializedName("humidity")
	val humidity: List<HumidityItem>,

	@field:SerializedName("PM10")
	val pM10: List<PM10Item>,

	@field:SerializedName("wind_speed")
	val windSpeed: List<WindSpeedItem>,

	@field:SerializedName("accumulated_rain")
	val accumulatedRain: List<AccumulatedRainItem>,

	@field:SerializedName("wind_direction")
	val windDirection: List<WindDirectionItem>,

	@field:SerializedName("PM1.0")
	val pM1_0: List<PM1_0Item>
)