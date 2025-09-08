package ru.example.canlisu.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import ru.example.canlisu.data.SubscriptionPlan
import ru.example.canlisu.databinding.FragmentChangeSubscriptionBinding

class ChangeSubscriptionFragment : Fragment() {

    private var _binding: FragmentChangeSubscriptionBinding? = null
    private val binding get() = _binding!!

    private val homePlans = listOf(
        SubscriptionPlan(
            title = "1 месяц",
            price = "1390 ₽",
            advantages = listOf("Фильтр", "Доставка", "Установка", "Обслуживание")
        ),
        SubscriptionPlan(
            title = "6 месяцев",
            price = "7490 ₽",
            oldPrice = "8340 ₽",
            discountPercent = 10,
            advantages = listOf("Фильтр", "Доставка", "Установка", "Обслуживание")
        ),
        SubscriptionPlan(
            title = "1 год",
            price = "14190 ₽",
            oldPrice = "16680 ₽",
            discountPercent = 15,
            advantages = listOf("Фильтр", "Доставка", "Установка", "Обслуживание")
        )
    )

    private val businessPlans = listOf(
        SubscriptionPlan(
            title = "1 месяц",
            price = "2390 ₽",
            advantages = listOf("Фильтр", "Доставка", "Установка", "Обслуживание")
        ),
        SubscriptionPlan(
            title = "6 месяцев",
            price = "12890 ₽",
            oldPrice = "14340 ₽",
            discountPercent = 10,
            advantages = listOf("Фильтр", "Доставка", "Установка", "Обслуживание")
        ),
        SubscriptionPlan(
            title = "1 год",
            price = "24190 ₽",
            oldPrice = "28380 ₽",
            discountPercent = 15,
            advantages = listOf("Фильтр", "Доставка", "Установка", "Обслуживание")
        )
    )

    private lateinit var adapter: SubscriptionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangeSubscriptionBinding.inflate(inflater, container, false)

        adapter = SubscriptionAdapter(emptyList()) { plan ->
            Toast.makeText(requireContext(), "Выбран тариф: ${plan.title}", Toast.LENGTH_SHORT).show()
        }
        binding.subscriptionRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.subscriptionRecyclerView.adapter = adapter
        adapter.submitList(homePlans)

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
