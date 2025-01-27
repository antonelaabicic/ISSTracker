package hr.algebra.isstracker.factory

import android.content.Context
import androidx.room.Room
import hr.algebra.isstracker.dao.AppDatabase
import hr.algebra.isstracker.dao.DataRepository

fun getRepository(context: Context): DataRepository {
    val database = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        AppDatabase.NAME
    ).build()

    val dao = database.dataDao()
    return DataRepository(dao)
}