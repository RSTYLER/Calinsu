package ru.example.canlisu.ui.gallery

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.launch
import ru.example.canlisu.R
import ru.example.canlisu.data.DbUserSubscriptionWithSub
import ru.example.canlisu.databinding.FragmentSubscriptionBinding

class SubscriptionFragment : Fragment() {

    private var _binding: FragmentSubscriptionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SubscriptionViewModel by viewModels()

    private lateinit var adapter: SubscriptionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSubscriptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = SubscriptionAdapter(emptyList()) { sub ->
            Log.d("SubscriptionFragment", "Open details for id=${sub.id}")
            findNavController().navigate(
                R.id.action_nav_gallery_to_subscriptionDetailFragment,
                bundleOf("subscriptionId" to sub.id)
            )
        }
        binding.subscriptionRecyclerView.layoutManager =
            LinearLayoutManager(requireContext())
        binding.subscriptionRecyclerView.adapter = adapter
        binding.tabLayout.isVisible = false
        binding.subscriptionRecyclerView.isVisible = false

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val isPhysical = tab?.position == 0
                Log.d("SubscriptionFragment", "Tab selected physical=$isPhysical")
                viewModel.loadSubscriptions(isPhysical)
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        binding.changeSubscriptionButton.setOnClickListener {
            binding.tabLayout.isVisible = true
            binding.subscriptionRecyclerView.isVisible = true
            viewModel.loadSubscriptions(true)
            binding.tabLayout.getTabAt(0)?.select()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.activeSubscription.collect { renderActiveSubscription(it) }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.subscriptions.collect { state ->
                when (state) {
                    is SubscriptionsUiState.Data -> {
                        adapter.submitList(state.items)
                    }
                    else -> {
                        // no-op for loading/empty/error for now
                    }
                }
            }
        }

        viewModel.loadActiveSubscription()
    }

    private fun renderActiveSubscription(sub: DbUserSubscriptionWithSub?) {
        if (sub == null) {
            binding.subStatusView.text = "У вас еще нет активной подписки"
            binding.subNameView.text = "Хотите выбрать?"
            binding.monthlyPaymentView.isVisible = false
            binding.lastBilledView.isVisible = false
            binding.changeSubscriptionButton.text = getString(R.string.choose_subscription)
        } else {
            val info = sub.subscription
            binding.subStatusView.text =
                getString(R.string.subscription_status, if (sub.is_active) "Активна" else "Не активна")
            binding.subNameView.text = info?.name ?: ""
            binding.monthlyPaymentView.isVisible = true
            binding.lastBilledView.isVisible = true
            binding.monthlyPaymentView.text = "Цена: ${info?.price?.toInt()} ₽"
            binding.lastBilledView.text = "Начало: ${sub.start_date ?: ""}"
            binding.changeSubscriptionButton.text = getString(R.string.change_subscription)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

