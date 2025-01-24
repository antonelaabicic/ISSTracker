package hr.algebra.isstracker.api

import com.google.gson.annotations.SerializedName

data class AstronautItem(
    @SerializedName("name") val name : String,
    @SerializedName("country") val country : String,
    @SerializedName("flag_code") val flagCode : String,
    @SerializedName("agency") val agency : String,
    @SerializedName("position") val position : String,
    @SerializedName("days_in_space") val daysInSpace : Int,
    @SerializedName("url") val url : String,
    @SerializedName("image") val image : String,
    @SerializedName("instagram") val instagram : String,
    @SerializedName("twitter") val twitter : String,
    @SerializedName("facebook") val facebook : String
)
