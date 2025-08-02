package ru.example.canlisu.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.example.canlisu.databinding.FragmentHomeBinding
import ru.example.canlisu.R


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // Примерные данные — потом можно подгружать из ViewModel или базы
    private val firstName = "Ivan"
    private val lastName = "Ivanov"

    private val email = "Ivan@example.com"
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
            // Эти ID должны быть прописаны в fragment_home.xml
            firstNameView.text = getString(R.string.first_name_template, firstName)
            lastNameView.text = getString(R.string.last_name_template, lastName)
            emailView.text = getString(R.string.email_template, email)
            addressView.text = getString(R.string.address_template, address)
            cardNumberMasked.text = getString(R.string.card_number_template, last4Digits)


        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
