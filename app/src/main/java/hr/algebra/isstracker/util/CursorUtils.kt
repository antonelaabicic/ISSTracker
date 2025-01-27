package hr.algebra.isstracker.util

import android.database.Cursor
import android.database.MatrixCursor
import hr.algebra.isstracker.model.Astronaut
import hr.algebra.isstracker.model.ISSLocation

fun List<Astronaut>.toCursor(): Cursor {
    val matrixCursor = MatrixCursor(
        arrayOf(
            "_id", "name", "country", "flagCode", "agency", "position", "daysInSpace",
            "description", "url", "image", "instagram", "twitter", "facebook", "isFavorite"
        )
    )

    for (astronaut in this) {
        matrixCursor.addRow(
            arrayOf(
                astronaut._id, astronaut.name, astronaut.country, astronaut.flagCode,
                astronaut.agency, astronaut.position, astronaut.daysInSpace, astronaut.description,
                astronaut.url, astronaut.image, astronaut.instagram, astronaut.twitter, astronaut.facebook,
                astronaut.isFavorite
            )
        )
    }

    return matrixCursor
}

fun ISSLocation.toCursor(): Cursor {
    val matrixCursor = MatrixCursor(
        arrayOf("location_id", "latitude", "longitude")
    )
    matrixCursor.addRow(arrayOf(this._id, this.latitude, this.longitude))
    return matrixCursor
}