package ru.example.canlisu.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.example.canlisu.databinding.FragmentSubscriptionBinding
import ru.example.canlisu.R

class GalleryFragment : Fragment() {

    private var _binding: FragmentSubscriptionBinding? = null
    private val binding get() = _binding!!

    // Примерные данные подписки (можно потом подгружать с API или ViewModel)
    private val subscriptionStatus = "Active"
    private val subscriptionName = "Live water \"mini\""
    private val monthlyPayment = "990₽"
    private val lastBillingDate = "2025-07-15"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSubscriptionBinding.inflate(inflater, container, false)

        binding.apply {
            subStatusView.text = getString(R.string.subscription_status, subscriptionStatus)
            subNameView.text = getString(R.string.subscription_name, subscriptionName)
            monthlyPaymentView.text = getString(R.string.monthly_payment, monthlyPayment)
            lastBilledView.text = getString(R.string.last_billing_date, lastBillingDate)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
