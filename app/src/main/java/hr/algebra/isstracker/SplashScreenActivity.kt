package hr.algebra.isstracker

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import hr.algebra.isstracker.databinding.ActivitySplashScreenBinding
import hr.algebra.isstracker.framework.applyAnimation
import hr.algebra.isstracker.framework.startActivity

private const val DELAY = 10000L

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        redirect()
    }

    private fun redirect() {
        Handler(Looper.getMainLooper()).postDelayed(
            { startActivity<MainActivity>() },
            DELAY
        )
    }
}