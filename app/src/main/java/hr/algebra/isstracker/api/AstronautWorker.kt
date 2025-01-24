package hr.algebra.isstracker.api

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters


class AstronautWorker(private val context: Context, workerParamrs: WorkerParameters) : Worker(context, workerParamrs) {

    override fun doWork(): Result {
        AstronautFetcher(context).fetchItems()
        return Result.success()
    }
}