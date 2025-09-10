package ru.example.canlisu.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ru.example.canlisu.data.SupabaseRepository
import ru.example.canlisu.databinding.FragmentSubscriptionDetailBinding
import android.util.Log

class SubscriptionDetailFragment : Fragment() {

    private var _binding: FragmentSubscriptionDetailBinding? = null
    private val binding get() = _binding!!
    private val repository = SupabaseRepository()

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
        Log.d("SubscriptionDetail", "subscriptionId=$id")
        viewLifecycleOwner.lifecycleScope.launch {
            val sub = repository.getSubscriptionById(id)
            if (sub != null) {
                binding.titleView.text = sub.name
                binding.descriptionView.text = sub.description
                val duration = formatDuration(sub.duration_days)
                var priceText = "Цена: ${sub.price.toInt()} ₽\nСрок: $duration"
                if ((sub.discount ?: 0) > 0) {
                    priceText += "\nСкидка: ${sub.discount}%"
                }
                binding.priceView.text = priceText
            }
        }

        binding.continueButton.setOnClickListener {
            Log.d("SubscriptionDetail", "Continue clicked for id=$id")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

private fun formatDuration(days: Int): String = when (days) {
    365 -> "1 год"
    180 -> "6 мес"
    30 -> "1 мес"
    else -> "$days дн"
}
