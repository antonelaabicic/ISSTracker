package hr.algebra.isstracker

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.preference.PreferenceManager
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import hr.algebra.isstracker.api.ISSLocationWorker
import hr.algebra.isstracker.databinding.ActivitySplashScreenBinding
import hr.algebra.isstracker.framework.callDelayed
import hr.algebra.isstracker.framework.getBooleanPreference
import hr.algebra.isstracker.framework.isOnline
import hr.algebra.isstracker.framework.startActivity

private const val DELAY = 5000L
public const val DATA_IMPORTED = "hr.algebra.nasa.data_imported"

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        redirect()
    }

    private fun redirect() {
        if (getBooleanPreference(DATA_IMPORTED)) {
            callDelayed(DELAY) { startActivity<MainActivity>() }
        } else {
            if (isOnline()) {
                WorkManager.getInstance(this).apply {
                    enqueueUniqueWork(
                        DATA_IMPORTED,
                        ExistingWorkPolicy.KEEP,
                        OneTimeWorkRequest.from(ISSLocationWorker::class.java)
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