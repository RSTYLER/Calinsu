package ru.example.canlisu.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.example.canlisu.databinding.FragmentHomeBinding
import ru.example.canlisu.R


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // Примерные данные — потом можно подгружать из ViewModel или базы
    private val firstName = "Ivan"
    private val lastName = "Ivanov"

    private val email = "Ivan@example.com"
    private val phone = "+7 999 123-45-67"
    private val address = "123 Example Street, NY"

    val last4Digits = "1234" // заменить на реальные данные


    @SuppressLint("SetTextI18n")
    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        // Устанавливаем текст в нужные TextView
        binding.apply {
            // Устанавливаем значения в TextView без шаблонов
            firstNameView.text = firstName
            lastNameView.text = lastName
            emailView.text = email
            phoneView.text = phone
            addressView.text = address
            //cardNumberMasked.text = getString(R.string.card_number_template, last4Digits)
        }

       binding.logoutButton.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_loginFragment)
        }
        binding.swipeRefreshLayout.setOnRefreshListener {
            // TODO: обновить данные здесь (например, запрос в ViewModel)
            Toast.makeText(requireContext(), "Refreshing...", Toast.LENGTH_SHORT).show()
            binding.swipeRefreshLayout.isRefreshing = false
        }


        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
