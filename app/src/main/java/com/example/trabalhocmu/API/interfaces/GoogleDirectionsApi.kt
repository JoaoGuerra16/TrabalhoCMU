package com.example.trabalhocmu.API.interfaces

import com.example.trabalhocmu.API.DataClass.DirectionsResponse
import com.example.trabalhocmu.API.DataClass.GeocodingResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleDirectionsApi {
    @GET("maps/api/directions/json")
    suspend fun getDirections(
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("mode") mode: String = "driving",
        @Query("key") apiKey: String
    ): DirectionsResponse


    @GET("maps/api/geocode/json")
    suspend fun getCoordinates(
        @Query("address") address: String,
        @Query("key") apiKey: String
    ): GeocodingResponse
}
