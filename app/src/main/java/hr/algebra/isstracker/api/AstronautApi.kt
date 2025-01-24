package hr.algebra.isstracker.api

import retrofit2.Call
import retrofit2.http.GET

const val API_URL_ASTRONAUTS = "https://corquaid.github.io/international-space-station-APIs/JSON/"

interface AstronautApi {
    @GET("people-in-space.json")
    fun fetchAstronauts(): Call<AstronautsListItems>
}