package hr.algebra.isstracker.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import hr.algebra.isstracker.adapter.AstronautAdapter
import hr.algebra.isstracker.databinding.FragmentAstronautsBinding
import hr.algebra.isstracker.model.Astronaut
import hr.algebra.isstracker.framework.fetchAstronauts
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AstronautsFragment : Fragment() {

    private lateinit var astronauts: MutableList<Astronaut>
    private lateinit var binding: FragmentAstronautsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAstronautsBinding.inflate(inflater, container, false)

        requireContext().fetchAstronauts { astronautList ->
            astronauts = astronautList
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvAstronauts.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = AstronautAdapter(requireContext(), astronauts)
        }
    }
}
