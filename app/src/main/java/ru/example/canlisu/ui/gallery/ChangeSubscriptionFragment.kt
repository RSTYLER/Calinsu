package ru.example.canlisu.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.launch
import ru.example.canlisu.R
import ru.example.canlisu.data.Subscription
import ru.example.canlisu.data.SubscriptionPlan
import ru.example.canlisu.data.SubscriptionRepository
import ru.example.canlisu.databinding.FragmentChangeSubscriptionBinding

class ChangeSubscriptionFragment : Fragment() {

    private var _binding: FragmentChangeSubscriptionBinding? = null
    private val binding get() = _binding!!

    private val repository = SubscriptionRepository()

    private var homePlans: List<SubscriptionPlan> = emptyList()
    private var businessPlans: List<SubscriptionPlan> = emptyList()

    private lateinit var adapter: SubscriptionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentChangeSubscriptionBinding.inflate(inflater, container, false)

        adapter = SubscriptionAdapter(emptyList()) { plan ->
            findNavController().navigate(
                R.id.action_changeSubscriptionFragment_to_subscriptionDetailFragment,
                bundleOf("subscriptionId" to plan.id)
            )
        }
        binding.subscriptionRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.subscriptionRecyclerView.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            repository.getSubscriptions(true).onSuccess { list ->
                homePlans = list.map { it.toPlan() }
                if (binding.tabLayout.selectedTabPosition == 0) {
                    adapter.submitList(homePlans)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repository.getSubscriptions(false).onSuccess { list ->
                businessPlans = list.map { it.toPlan() }
            }
        }

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> adapter.submitList(homePlans)
                    1 -> adapter.submitList(businessPlans)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        return binding.root
    }

    private fun Subscription.toPlan(): SubscriptionPlan {
        val old = discount?.let { (price / (1 - it / 100.0)).toInt().toString() + " ₽" }
        return SubscriptionPlan(
            id = id,
            title = name,
            price = "${price.toInt()} ₽",
            oldPrice = old,
            discountPercent = discount,
            advantages = listOf("Фильтр", "Доставка", "Установка", "Обслуживание")
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
