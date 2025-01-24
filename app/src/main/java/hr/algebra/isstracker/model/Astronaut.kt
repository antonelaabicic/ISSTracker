package hr.algebra.isstracker.model

data class Astronaut(
    var _id :Long?,
    val name: String,
    val country: String,
    val flagCode: String,
    val agency: String,
    val position: String,
    val daysInSpace: Double,
    val description: String?,
    val url: String,
    val image: String,
    val instagram: String?,
    val twitter: String?,
    val facebook: String?,
    var isFavorite: Boolean = false
)


