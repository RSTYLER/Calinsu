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
import ru.example.canlisu.databinding.FragmentPasswordRecoveryBinding

class PasswordRecoveryFragment : Fragment() {

    private var _binding: FragmentPasswordRecoveryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PasswordRecoveryViewModel by viewModels {
        PasswordRecoveryViewModelFactory(AuthRepository())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPasswordRecoveryBinding.inflate(inflater, container, false)

        viewModel.resetState.observe(viewLifecycleOwner) { state ->
            when (state) {
                AuthState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.sendButton.isEnabled = false
                }
                is AuthState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.sendButton.isEnabled = true
                    Snackbar.make(
                        binding.root,
                        getString(R.string.password_recovery_success),
                        Snackbar.LENGTH_LONG
                    ).show()
                    findNavController().navigate(R.id.action_passwordRecoveryFragment_to_loginFragment)
                }
                is AuthState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.sendButton.isEnabled = true
                    Snackbar.make(
                        binding.root,
                        state.message.ifEmpty { getString(R.string.error_network) },
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }

        binding.sendButton.setOnClickListener {
            val email = binding.emailInput.text?.toString()?.trim() ?: ""
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.emailLayout.error = getString(R.string.error_invalid_email)
            } else {
                binding.emailLayout.error = null
                viewModel.resetPassword(email)
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
