package ru.example.canlisu.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.util.Patterns
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.example.canlisu.R
import ru.example.canlisu.databinding.FragmentRegistrationBinding

class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RegistrationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)

        binding.signUpButton.setOnClickListener {
            val firstName = binding.firstNameInput.text?.toString()?.trim() ?: ""
            val lastName = binding.lastNameInput.text?.toString()?.trim() ?: ""
            val email = binding.emailInput.text?.toString()?.trim() ?: ""
            val phone = binding.phoneInput.text?.toString()?.trim() ?: ""

            val nameRegex = Regex("^[А-Яа-яЁё]+$")
            var isValid = true

            if (!nameRegex.matches(firstName)) {
                binding.firstNameLayout.error = getString(R.string.error_invalid_name)
                isValid = false
            } else {
                binding.firstNameLayout.error = null
            }

            if (!nameRegex.matches(lastName)) {
                binding.lastNameLayout.error = getString(R.string.error_invalid_name)
                isValid = false
            } else {
                binding.lastNameLayout.error = null
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.emailLayout.error = getString(R.string.error_invalid_email)
                isValid = false
            } else {
                binding.emailLayout.error = null
            }

            if (!Regex("^\\+7\\d{10}").matches(phone)) {
                binding.phoneLayout.error = getString(R.string.error_invalid_phone)
                isValid = false
            } else {
                binding.phoneLayout.error = null
            }

            if (isValid) {
                findNavController().navigate(R.id.action_registrationFragment_to_nav_home)
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

