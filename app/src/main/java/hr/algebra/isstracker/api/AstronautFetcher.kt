package hr.algebra.isstracker.api

import android.content.ContentValues
import android.content.Context
import android.util.Log
import hr.algebra.isstracker.ASTRONAUT_CONTENT_URI
import hr.algebra.isstracker.DataReceiver
import hr.algebra.isstracker.framework.sendBroadcast
import hr.algebra.isstracker.model.Astronaut
import hr.algebra.isstracker.model.ISSLocation
import hr.algebra.isstracker.scraping.fetchWikipediaDescription
import hr.algebra.nasa.handler.downloadImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AstronautFetcher(private val context: Context) {

    private var astronautApi: AstronautApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(API_URL_ASTRONAUTS)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        astronautApi = retrofit.create(AstronautApi::class.java)
    }

    fun fetchItems() {
        val request = astronautApi.fetchAstronauts()
        request.enqueue(object : Callback<AstronautsListItems> {
            override fun onResponse(
                call: Call<AstronautsListItems>,
                response: Response<AstronautsListItems>
            ) {
                response.body()?.let { populateItems(it.astronauts) }
            }

            override fun onFailure(call: Call<AstronautsListItems>, t: Throwable) {
                Log.d(javaClass.name, t.message, t)
            }
        })
    }

    private fun populateItems(astronautItems: List<AstronautItem>) {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            astronautItems
                .filter { it.flagCode != "cn" }
                .forEach {
                    val values = ContentValues().apply {
                        put("name", it.name)
                        put("country", it.country)
                        put("flagCode", it.flagCode)
                        put("agency", it.agency)
                        put("position", it.position)
                        put("daysInSpace", it.daysInSpace.toDouble())
                        put("description", fetchWikipediaDescription(it.url))
                        put("url", it.url)
                        put("image", downloadImage(context, it.image) ?: "")
                        put("instagram", it.instagram.takeIf { it.isNotEmpty() })
                        put("twitter", it.twitter.takeIf { it.isNotEmpty() })
                        put("facebook", it.facebook.takeIf { it.isNotEmpty() })
                        put("isFavorite", false)
                    }
                    context.contentResolver.insert(ASTRONAUT_CONTENT_URI, values)
                }
            context.sendBroadcast<DataReceiver>()
        }
    }
}