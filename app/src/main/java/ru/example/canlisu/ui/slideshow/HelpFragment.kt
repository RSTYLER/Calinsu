package ru.example.canlisu.ui.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.chip.Chip
import androidx.navigation.fragment.findNavController
import ru.example.canlisu.R
import ru.example.canlisu.databinding.FragmentHelpBinding


class HelpFragment : Fragment() {

    private var _binding: FragmentHelpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHelpBinding.inflate(inflater, container, false)

        binding.createRequestButton.setOnClickListener {
            // Навигация к экрану RequestFragment
            findNavController().navigate(R.id.action_nav_slideshow_to_requestFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val number = (10000..99999).random()
        val card = view.findViewById<View>(R.id.exampleRequest)
        card.findViewById<TextView>(R.id.tvRequestNumber).text = "№ $number"
        card.findViewById<TextView>(R.id.tvRequestTitle).text = "Протечка трубы"
        card.findViewById<Chip>(R.id.chipStatus).text = "Исполнен"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
