package hr.algebra.isstracker

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import hr.algebra.isstracker.adapter.AstronautPagerAdapter
import hr.algebra.isstracker.databinding.ActivityAstronautPagerBinding
import hr.algebra.isstracker.framework.fetchAstronauts
import hr.algebra.isstracker.model.Astronaut

const val ASTRONAUT_POSITION = "hr.algebra.isstracker.item_position"

class AstronautPagerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAstronautPagerBinding
    private lateinit var astronauts: MutableList<Astronaut>
    private var astronautPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAstronautPagerBinding.inflate(layoutInflater)

        fetchAstronauts { astronautList ->
            astronauts = astronautList
        }

        setContentView(binding.root)

        initPager()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun initPager() {
        // fetch here
        astronautPosition = intent.getIntExtra(ASTRONAUT_POSITION, astronautPosition)
        binding.viewPager.adapter = AstronautPagerAdapter(this, astronauts)
        binding.viewPager.currentItem = astronautPosition
    }
}