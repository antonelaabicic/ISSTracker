package hr.algebra.isstracker.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import hr.algebra.isstracker.model.Astronaut
import hr.algebra.isstracker.model.ISSLocation

@Dao
interface DataDao {
    @Query("SELECT * FROM astronaut")
    fun getAllAstronauts(): List<Astronaut>

    @Query("SELECT * FROM astronaut WHERE _id = :astronautId LIMIT 1")
    fun getAstronaut(astronautId: Long): Astronaut?

    @Insert
    fun insertAstronaut(astronaut: Astronaut): Long

    @Update
    fun updateAstronaut(astronaut: Astronaut): Int

    @Query("DELETE FROM astronaut WHERE _id = :astronautId")
    fun deleteAstronaut(astronautId: Long): Int

    @Query("SELECT * FROM iss_location LIMIT 1")
    fun getLocation(): ISSLocation?

    @Insert
    fun insertLocation(location: ISSLocation): Long
}