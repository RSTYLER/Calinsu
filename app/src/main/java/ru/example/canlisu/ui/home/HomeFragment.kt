package ru.example.canlisu.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.example.canlisu.databinding.FragmentHomeBinding
import ru.example.canlisu.R
import ru.example.canlisu.data.UserManager
import ru.example.canlisu.prefs.AuthPrefs

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    val last4Digits = "1234" // заменить на реальные данные

    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        viewModel.user.observe(viewLifecycleOwner) { user ->
            binding.firstNameView.text = user?.firstName.orEmpty()
            binding.lastNameView.text = user?.lastName.orEmpty()
            binding.emailView.text = user?.email.orEmpty()
            binding.phoneView.text = user?.phone.orEmpty()
            binding.addressView.text = ""
            //cardNumberMasked.text = getString(R.string.card_number_template, last4Digits)
        }

       binding.logoutButton.setOnClickListener {
            AuthPrefs.clear(requireContext())
            UserManager.currentUser = null
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
