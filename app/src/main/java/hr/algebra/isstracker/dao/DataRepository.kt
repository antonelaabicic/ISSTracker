package hr.algebra.isstracker.dao

import android.content.ContentValues
import hr.algebra.isstracker.model.Astronaut
import hr.algebra.isstracker.model.ISSLocation

class DataRepository(private val dao: DataDao) {

    fun getAllAstronauts(): List<Astronaut> {
        return dao.getAllAstronauts()
    }

    fun getAstronaut(astronautId: Long): Astronaut? {
        return dao.getAstronaut(astronautId)
    }

    fun insertAstronaut(values: ContentValues?): Long {
        val astronaut = Astronaut(
            null,
            values?.getAsString("name") ?: "",
            values?.getAsString("country") ?: "",
            values?.getAsString("flagCode") ?: "",
            values?.getAsString("agency") ?: "",
            values?.getAsString("position") ?: "",
            values?.getAsDouble("daysInSpace") ?: 0.0,
            values?.getAsString("description"),
            values?.getAsString("url") ?: "",
            values?.getAsString("image") ?: "",
            values?.getAsString("instagram"),
            values?.getAsString("twitter"),
            values?.getAsString("facebook"),
            values?.getAsBoolean("isFavorite") ?: false
        )
        return dao.insertAstronaut(astronaut)
    }

    fun updateAstronaut(values: ContentValues?, astronautId: Long): Int {
        val existingAstronaut = dao.getAstronaut(astronautId)

        val updatedAstronaut = Astronaut(
            astronautId,
            values?.getAsString("name") ?: existingAstronaut!!.name,
            values?.getAsString("country") ?: existingAstronaut!!.country,
            values?.getAsString("flagCode") ?: existingAstronaut!!.flagCode,
            values?.getAsString("agency") ?: existingAstronaut!!.agency,
            values?.getAsString("position") ?: existingAstronaut!!.position,
            values?.getAsDouble("daysInSpace") ?: existingAstronaut!!.daysInSpace,
            values?.getAsString("description") ?: existingAstronaut!!.description,
            values?.getAsString("url") ?: existingAstronaut!!.url,
            values?.getAsString("image") ?: existingAstronaut!!.image,
            values?.getAsString("instagram") ?: existingAstronaut!!.instagram,
            values?.getAsString("twitter") ?: existingAstronaut!!.twitter,
            values?.getAsString("facebook") ?: existingAstronaut!!.facebook,
            values?.getAsBoolean("isFavorite") ?: existingAstronaut!!.isFavorite
        )
        return dao.updateAstronaut(updatedAstronaut)
    }

    fun deleteAstronaut(astronautId: Long): Int {
        return dao.deleteAstronaut(astronautId)
    }

    fun getLocation(): ISSLocation? {
        return dao.getLocation()
    }

    fun insertLocation(values: ContentValues?): Long {
        val location = ISSLocation(
            null,
            values?.getAsDouble("latitude") ?: 0.0,
            values?.getAsDouble("longitude") ?: 0.0
        )
        return dao.insertLocation(location)
    }
}
