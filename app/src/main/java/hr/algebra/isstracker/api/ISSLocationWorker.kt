package hr.algebra.isstracker.api

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters


class ISSLocationWorker(private val context: Context, workerParamrs: WorkerParameters) : Worker(context, workerParamrs) {
    fun fetchItems(count: Int) {
        Thread.sleep(6000)
    }

    override fun doWork(): Result {
        ISSLocationFetcher(context).fetchISSLocation()
        return Result.success()
    }
}