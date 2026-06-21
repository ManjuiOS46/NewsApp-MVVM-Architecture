package com.joydipbhakat.newsapp.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.joydipbhakat.newsapp.data.local.DatabaseService
import com.joydipbhakat.newsapp.data.network.api.NetworkService
import com.joydipbhakat.newsapp.data.network.models.toArticleEntity
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.*
import java.util.concurrent.TimeUnit

@HiltWorker
class ArticleUpdateWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val networkService: NetworkService,
    private val databaseService: DatabaseService
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val networkNews = networkService.getTopHeadline("us").articles.map {
                it.toArticleEntity()
            }
            databaseService.clearAndInsert(networkNews)
            schedule(applicationContext)
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    companion object {
        fun schedule(context: Context) {
            val currentDate = Calendar.getInstance()
            val dueDate = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, 6)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }

            // If it's already past 6 AM today, schedule it for tomorrow
            if (dueDate.before(currentDate)) {
                dueDate.add(Calendar.DAY_OF_MONTH, 1)
            }

            val timeDiff = dueDate.timeInMillis - currentDate.timeInMillis

            // Define constraints if needed (e.g., requires internet)
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val dailyWorkRequest = OneTimeWorkRequestBuilder<ArticleUpdateWorker>()
                .setInitialDelay(timeDiff, TimeUnit.SECONDS)
                .setConstraints(constraints)
                .build()

            // Use KEEP policy to avoid overriding a job that is already waiting in line
            WorkManager.getInstance(context).enqueueUniqueWork(
                "DailyDataUpdateWork",
                ExistingWorkPolicy.REPLACE,
                dailyWorkRequest
            )
        }
    }
}