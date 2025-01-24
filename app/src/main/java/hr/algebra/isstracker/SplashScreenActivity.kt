package hr.algebra.isstracker

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import hr.algebra.isstracker.api.AstronautWorker
import hr.algebra.isstracker.api.ISSLocationWorker
import hr.algebra.isstracker.databinding.ActivitySplashScreenBinding
import hr.algebra.isstracker.framework.callDelayed
import hr.algebra.isstracker.framework.getBooleanPreference
import hr.algebra.isstracker.framework.isOnline
import hr.algebra.isstracker.framework.startActivity

private const val DELAY = 5000L
public const val DATA_IMPORTED_ISS_LOCATION = "hr.algebra.nasa.iss_location.data_imported"
public const val DATA_IMPORTED_ASTRONAUTS = "hr.algebra.nasa.astronauts.data_imported"

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        redirect()
    }

    private fun redirect() {
        if (getBooleanPreference(DATA_IMPORTED_ISS_LOCATION) && getBooleanPreference(DATA_IMPORTED_ASTRONAUTS)) {
            callDelayed(DELAY) { startActivity<MainActivity>() }
        } else {
            if (isOnline()) {
                WorkManager.getInstance(this).apply {
                    enqueueUniqueWork(
                        DATA_IMPORTED_ISS_LOCATION,
                        ExistingWorkPolicy.KEEP,
                        OneTimeWorkRequest.from(ISSLocationWorker::class.java)
                    )
                }
                WorkManager.getInstance(this).apply {
                    enqueueUniqueWork(
                        DATA_IMPORTED_ASTRONAUTS,
                        ExistingWorkPolicy.KEEP,
                        OneTimeWorkRequest.from(AstronautWorker::class.java)
                    )
                }
            } else {
                showNoInternetAnimation()
                callDelayed(DELAY) { finish() }
            }
        }
    }

    private fun showNoInternetAnimation() {
        binding.satelliteAnimation.visibility = View.GONE
        binding.loadingAnimation.visibility = View.GONE

        val noInternetAnimation = LottieAnimationView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                0,
                0.75f
            )
            setAnimation(R.raw.no_internet)
            repeatCount = LottieDrawable.INFINITE
            playAnimation()
        }
        binding.main.addView(noInternetAnimation, 0)
    }
}