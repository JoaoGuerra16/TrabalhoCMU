package com.example.trabalhocmu.API.DataClass

data class GeocodingResponse(
    val results: List<GeocodingResult>
)

data class GeocodingResult(
    val formatted_address: String,
    val geometry: Geometry
)

data class Geometry(
    val location: Location
)

data class Location(
    val lat: Double,
    val lng: Double
)
