package ru.example.canlisu.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.util.Patterns
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.example.canlisu.databinding.FragmentLoginBinding
import ru.example.canlisu.R

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.signInButton.setOnClickListener {
            val login = binding.emailInput.text?.toString()?.trim() ?: ""
            val password = binding.passwordInput.text?.toString() ?: ""
            val isEmail = Patterns.EMAIL_ADDRESS.matcher(login).matches()
            val isPhone = Regex("^\\+7\\d{10}").matches(login)
            if (!isEmail && !isPhone) {
                binding.emailLayout.error = getString(R.string.error_invalid_email_or_phone)
            } else {
                binding.emailLayout.error = null
                // TODO: replace with hashed password from database
                val storedHash = ""
                if (viewModel.verifyPassword(password, storedHash)) {
                    binding.passwordLayout.error = null
                    binding.passwordInput.text?.clear()
                    findNavController().navigate(R.id.action_loginFragment_to_nav_home)
                } else {
                    binding.passwordLayout.error = getString(R.string.error_invalid_password)
                }
            }
            binding.passwordInput.text?.clear()
        }

        binding.createAccountButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
