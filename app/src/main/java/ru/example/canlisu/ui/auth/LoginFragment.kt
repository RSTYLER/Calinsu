package ru.example.canlisu.ui.auth

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import ru.example.canlisu.R
import ru.example.canlisu.data.AuthRepository
import ru.example.canlisu.data.UserManager
import ru.example.canlisu.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels {
        LoginViewModelFactory(AuthRepository())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        viewModel.loginState.observe(viewLifecycleOwner) { state ->
            when (state) {
                AuthState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.signInButton.isEnabled = false
                }
                is AuthState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.signInButton.isEnabled = true
                    binding.emailLayout.error = null
                    binding.passwordLayout.error = null
                    binding.passwordInput.text?.clear()
                    UserManager.currentUser = state.data
                    findNavController().navigate(R.id.action_loginFragment_to_nav_home)
                }
                is AuthState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.signInButton.isEnabled = true
                    when (state.message) {
                        "user_not_found" -> {
                            binding.emailLayout.error = getString(R.string.error_user_not_found)
                            binding.passwordLayout.error = null
                        }
                        "invalid_password" -> {
                            binding.passwordLayout.error = getString(R.string.error_invalid_password)
                            binding.emailLayout.error = null
                        }
                        else -> {
                            binding.emailLayout.error = null
                            binding.passwordLayout.error = null
                            Snackbar.make(
                                binding.root,
                                state.message.ifEmpty { getString(R.string.error_network) },
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }

        binding.signInButton.setOnClickListener {
            val login = binding.emailInput.text?.toString()?.trim() ?: ""
            val password = binding.passwordInput.text?.toString() ?: ""
            val isEmail = Patterns.EMAIL_ADDRESS.matcher(login).matches()
            val isPhone = Regex("^\\+7\\d{10}").matches(login)
            if (!isEmail && !isPhone) {
                binding.emailLayout.error = getString(R.string.error_invalid_email_or_phone)
            } else {
                binding.emailLayout.error = null
                if (password.isEmpty()) {
                    binding.passwordLayout.error = getString(R.string.error_invalid_password)
                } else {
                    binding.passwordLayout.error = null
                    viewModel.login(login, password)
                }
            }
        }

        binding.createAccountButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
        }

        binding.forgotLink.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_passwordRecoveryFragment)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
