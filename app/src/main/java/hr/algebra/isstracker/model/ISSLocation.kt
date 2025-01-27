package hr.algebra.isstracker.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "iss_location")
data class ISSLocation(
    @PrimaryKey(autoGenerate = true)
    var _id :Long?,
    val latitude: Double,
    val longitude: Double
)
