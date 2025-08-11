package ru.example.canlisu.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import ru.example.canlisu.R
import ru.example.canlisu.data.auth.LoginRequest
import ru.example.canlisu.data.net.Network
import ru.example.canlisu.data.prefs.AuthPrefs
import ru.example.canlisu.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            AuthPrefs.savedEmail(requireContext()).collect { saved ->
                if (saved.isNotEmpty() && binding.emailInput.text.isNullOrEmpty()) {
                    binding.emailInput.setText(saved)
                    binding.rememberCheck.isChecked = true
                }
            }
        }

        binding.signInButton.setOnClickListener {
            val email = binding.emailInput.text?.toString()?.trim().orEmpty()
            val password = binding.passwordInput.text?.toString().orEmpty()

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.emailLayout.error = getString(R.string.login_email_label) + " is invalid"
                return@setOnClickListener
            } else binding.emailLayout.error = null

            if (password.isEmpty()) {
                binding.passwordLayout.error = "Password is required"
                return@setOnClickListener
            } else binding.passwordLayout.error = null

            signIn(email, password)
        }
    }

    private fun signIn(email: String, password: String) {
        binding.signInButton.isEnabled = false
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val resp = Network.authApi.login(LoginRequest(email, password))
                if (resp.isSuccessful && resp.body()?.user != null) {
                    // если включено "Запомнить меня" — сохраняем
                    if (binding.rememberCheck.isChecked) {
                        AuthPrefs.setLoggedIn(requireContext(), true)
                        AuthPrefs.setEmail(requireContext(), email)
                    } else {
                        // не запоминать — очищаем
                        AuthPrefs.clear(requireContext())
                    }
                    navigateToHome()
                } else {
                    Toast.makeText(requireContext(), "Wrong email or password", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Network error: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                binding.signInButton.isEnabled = true
            }
        }
    }

    private fun navigateToHome() {
        val options = NavOptions.Builder()
            .setPopUpTo(findNavController().graph.startDestinationId, true)
            .build()
        findNavController().navigate(R.id.nav_home, null, options)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
