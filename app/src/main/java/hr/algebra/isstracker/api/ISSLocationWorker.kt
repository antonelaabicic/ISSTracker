package hr.algebra.isstracker.api

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters


class ISSLocationWorker(private val context: Context, workerParamrs: WorkerParameters) : Worker(context, workerParamrs) {

    override fun doWork(): Result {
        ISSLocationFetcher(context).fetchItem()
        return Result.success()
    }
}