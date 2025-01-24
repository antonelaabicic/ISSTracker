package hr.algebra.isstracker.api

import com.google.gson.annotations.SerializedName

data class AstronautsListItems(
    @SerializedName("people") val astronauts: List<AstronautItem>
)
