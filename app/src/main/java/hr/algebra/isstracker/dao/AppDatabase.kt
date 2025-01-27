package hr.algebra.isstracker.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import hr.algebra.isstracker.model.Astronaut
import hr.algebra.isstracker.model.ISSLocation

@Database(entities = [Astronaut::class, ISSLocation::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val NAME = "ISSTracker_db"
    }
    abstract fun dataDao(): DataDao
}