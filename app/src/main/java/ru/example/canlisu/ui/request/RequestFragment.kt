package ru.example.canlisu.ui.request

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import ru.example.canlisu.databinding.FragmentRequestBinding

class RequestFragment : Fragment() {

    private var _binding: FragmentRequestBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRequestBinding.inflate(inflater, container, false)

        binding.submitRequestButton.setOnClickListener {
            val issue = binding.issueInput.text.toString()
            val phone = binding.phoneInput.text.toString()

            if (issue.isBlank() || phone.isBlank()) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Request submitted!", Toast.LENGTH_SHORT).show()
                // TODO: отправка на сервер или сохранение в базу
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
