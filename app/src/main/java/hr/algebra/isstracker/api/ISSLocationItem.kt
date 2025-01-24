package hr.algebra.isstracker.api

import com.google.gson.annotations.SerializedName

data class ISSLocationItem(
    @SerializedName("latitude") val latitude: String,
    @SerializedName("longitude") val longitude: String
)

