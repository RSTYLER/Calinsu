package ru.example.canlisu.ui.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import ru.example.canlisu.databinding.FragmentHelpBinding

class SlideshowFragment : Fragment() {

    private var _binding: FragmentHelpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHelpBinding.inflate(inflater, container, false)

        // Кнопка "Create New Request"
        binding.createRequestButton.setOnClickListener {
            Toast.makeText(requireContext(), "Create Request clicked", Toast.LENGTH_SHORT).show()
            // TODO: Навигация или открытие формы заявки
        }

        // Здесь будет логика адаптера RecyclerView позже

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
