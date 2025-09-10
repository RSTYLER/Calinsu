package ru.example.canlisu.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ru.example.canlisu.data.SubscriptionRepository
import ru.example.canlisu.databinding.FragmentSubscriptionDetailBinding

class SubscriptionDetailFragment : Fragment() {

    private var _binding: FragmentSubscriptionDetailBinding? = null
    private val binding get() = _binding!!
    private val repository = SubscriptionRepository()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSubscriptionDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = requireArguments().getInt("subscriptionId")
        viewLifecycleOwner.lifecycleScope.launch {
            repository.getSubscriptionById(id).onSuccess { sub ->
                binding.titleView.text = sub.name
                binding.descriptionView.text = sub.description
                binding.priceView.text = "${sub.price.toInt()} ₽"
            }
        }

        binding.continueButton.setOnClickListener { }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
