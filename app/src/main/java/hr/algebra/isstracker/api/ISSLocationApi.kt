package hr.algebra.isstracker.api

import retrofit2.Call
import retrofit2.http.GET

const val API_URL_ISS_LOCATION = "https://api.wheretheiss.at/v1/satellites/"

interface ISSLocationApi {
    @GET("25544")
    fun fetchISSLocation(): Call<ISSLocationItem>
}


