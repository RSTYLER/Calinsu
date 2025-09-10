package ru.example.canlisu.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import ru.example.canlisu.R
import ru.example.canlisu.prefs.AuthPrefs
import ru.example.canlisu.data.SubscriptionRepository
import ru.example.canlisu.data.UserSubscription
import ru.example.canlisu.databinding.FragmentSubscriptionBinding

class SubscriptionFragment : Fragment() {

    private var _binding: FragmentSubscriptionBinding? = null
    private val binding get() = _binding!!

    private val repository = SubscriptionRepository()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSubscriptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val user = AuthPrefs.getUser(requireContext())
        val userId = user?.id
        if (userId == null) {
            showNoSubscription()
            return
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repository.getActiveSubscription(userId).onSuccess { sub ->
                if (sub != null && sub.isActive) {
                    showSubscription(sub)
                } else {
                    showNoSubscription()
                }
            }.onFailure {
                showNoSubscription()
            }
        }
    }

    private fun showSubscription(sub: UserSubscription) {
        binding.apply {
            subStatusView.text = getString(R.string.subscription_status, if (sub.isActive) "Активна" else "Не активна")
            subNameView.text = "ID подписки: ${sub.subscriptionId}"
            monthlyPaymentView.text = "ID записи: ${sub.id}"
            lastBilledView.text = "Дата начала: ${sub.startDate}"
            monthlyPaymentView.visibility = View.VISIBLE
            lastBilledView.visibility = View.VISIBLE
            changeSubscriptionButton.text = getString(R.string.change_subscription)
            changeSubscriptionButton.setOnClickListener {
                findNavController().navigate(R.id.action_nav_gallery_to_changeSubscriptionFragment)
            }
        }
    }

    private fun showNoSubscription() {
        binding.apply {
            subStatusView.text = "У вас еще нет активной подписки"
            subNameView.text = "Хотите выбрать?"
            monthlyPaymentView.visibility = View.GONE
            lastBilledView.visibility = View.GONE
            changeSubscriptionButton.text = getString(R.string.choose_subscription)
            changeSubscriptionButton.setOnClickListener {
                findNavController().navigate(R.id.action_nav_gallery_to_changeSubscriptionFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
