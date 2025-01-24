package hr.algebra.isstracker.api

import android.content.Context
import android.util.Log
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
        val items = mutableListOf<Astronaut>()
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            astronautItems.forEach {
                if (it.flagCode == "cn") {
                    return@forEach
                }
                items.add(
                    Astronaut(
                        null,
                        it.name,
                        it.country,
                        it.flagCode,
                        it.agency,
                        it.position,
                        it.daysInSpace.toDouble(),
                        fetchWikipediaDescription(it.url),
                        it.url,
                        downloadImage(context, it.image) ?: "",
                        if (it.instagram.isEmpty()) null else it.instagram,
                        if (it.twitter.isEmpty()) null else it.twitter,
                        if (it.facebook.isEmpty()) null else it.facebook,
                        isFavorite = false
                    )
                )
            }
            context.sendBroadcast<DataReceiver>()
        }
    }
}