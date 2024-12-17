package com.example.trabalhocmu.API.DataClass

data class DirectionsResponse(
    val routes: List<Route>
)

data class Route(
    val overview_polyline: OverviewPolyline,
    val legs: List<Leg>
)

data class OverviewPolyline(
    val points: String
)

data class Leg(
    val start_address: String,
    val end_address: String,
    val steps: List<Step>
)

data class Step(
    val html_instructions: String,
    val distance: Distance
)

data class Distance(
    val text: String,
    val value: Int
)