package hr.algebra.isstracker.api

import android.content.Context
import android.util.Log
import hr.algebra.isstracker.DataReceiver
import hr.algebra.isstracker.framework.sendBroadcast
import hr.algebra.isstracker.model.ISSLocation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ISSLocationFetcher(private val context: Context) {

    private var issLocationApi: ISSLocationApi
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(API_URL_ISS_LOCATION)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        issLocationApi = retrofit.create(ISSLocationApi::class.java)
    }

    fun fetchItem() {
        val request = issLocationApi.fetchISSLocation()
        request.enqueue(object : Callback<ISSLocationItem> {
            override fun onResponse(
                call: Call<ISSLocationItem>,
                response: Response<ISSLocationItem>
            ) {
                response.body()?.let { populateItem(it) }
            }

            override fun onFailure(call: Call<ISSLocationItem>, t: Throwable) {
                Log.d(javaClass.name, t.message, t)
            }
        })
    }

    private fun populateItem(issLocationItem: ISSLocationItem) {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            val latitude = issLocationItem.latitude.toDoubleOrNull() ?: 0.0
            val longitude = issLocationItem.longitude.toDoubleOrNull() ?: 0.0

            var isslocation = ISSLocation(
                null,
                issLocationItem.latitude.toDoubleOrNull() ?: 0.0,
                issLocationItem.longitude.toDoubleOrNull() ?: 0.0
            )
            context.sendBroadcast<DataReceiver>()
        }
    }
}