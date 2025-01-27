package hr.algebra.isstracker

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import hr.algebra.isstracker.dao.DataRepository
import hr.algebra.isstracker.factory.getRepository
import hr.algebra.isstracker.util.toCursor

private const val AUTHORITY = "hr.algebra.isstracker.api.provider"
private const val ASTRONAUT_PATH = "astronauts"
private const val LOCATION_PATH = "location"

val ASTRONAUT_CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/$ASTRONAUT_PATH")
val LOCATION_CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/$LOCATION_PATH")

private const val ASTRONAUTS = 100
private const val ASTRONAUT_ID = 101
private const val LOCATION_ID = 201

private val URI_MATCHER = with(UriMatcher(UriMatcher.NO_MATCH)) {
    addURI(AUTHORITY, ASTRONAUT_PATH, ASTRONAUTS)
    addURI(AUTHORITY, "$ASTRONAUT_PATH/#", ASTRONAUT_ID)
    addURI(AUTHORITY, LOCATION_PATH, LOCATION_ID)
    this
}

class DataContentProvider : ContentProvider() {

    private lateinit var repository: DataRepository
    override fun onCreate(): Boolean {
        repository = getRepository(context!!)
        return true
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return when (URI_MATCHER.match(uri)) {
            ASTRONAUTS -> {
                val astronautId = selectionArgs?.get(0)?.toLong() ?: throw IllegalArgumentException("Astronaut ID required")
                repository.deleteAstronaut(astronautId)
            }
            ASTRONAUT_ID -> {
                val astronautId = ContentUris.parseId(uri)
                repository.deleteAstronaut(astronautId)
            }
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        when (URI_MATCHER.match(uri)) {
            ASTRONAUTS -> {
                val id = repository.insertAstronaut(values)
                return ContentUris.withAppendedId(ASTRONAUT_CONTENT_URI, id)
            }
            LOCATION_ID -> {
                val id = repository.insertLocation(values)
                return ContentUris.withAppendedId(LOCATION_CONTENT_URI, id)
            }
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return when (URI_MATCHER.match(uri)) {
            ASTRONAUTS -> {
                val astronauts = repository.getAllAstronauts()
                astronauts.toCursor()
            }
            ASTRONAUT_ID -> {
                val astronautId = ContentUris.parseId(uri)
                val astronaut = repository.getAstronaut(astronautId)
                astronaut?.let {
                    listOf(it).toCursor()
                } ?: throw IllegalArgumentException("Astronaut not found")
            }
            LOCATION_ID -> {
                val location = repository.getLocation()
                location?.toCursor()
            }
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return when (URI_MATCHER.match(uri)) {
            ASTRONAUTS -> {
                val astronautId = selectionArgs?.get(0)?.toLong() ?: throw IllegalArgumentException("Astronaut ID required")
                repository.updateAstronaut(values, astronautId)
            }
            ASTRONAUT_ID -> {
                val astronautId = ContentUris.parseId(uri)
                repository.updateAstronaut(values, astronautId)
            }
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun getType(uri: Uri): String? {
        TODO(
            "Implement this to handle requests for the MIME type of the data" +
                    "at the given URI"
        )
    }
}