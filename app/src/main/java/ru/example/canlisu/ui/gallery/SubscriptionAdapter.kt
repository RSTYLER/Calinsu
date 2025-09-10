package ru.example.canlisu.ui.gallery

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.example.canlisu.data.DbSubscription
import ru.example.canlisu.databinding.ItemSubscriptionBinding

class SubscriptionAdapter(
    private var items: List<DbSubscription>,
    private val onSelect: (DbSubscription) -> Unit,
) : RecyclerView.Adapter<SubscriptionAdapter.SubscriptionViewHolder>() {

    inner class SubscriptionViewHolder(private val binding: ItemSubscriptionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(plan: DbSubscription) {
            binding.planTitle.text = plan.name
            binding.planPrice.text = "${plan.price.toInt()} ₽"

            val discount = plan.discount ?: 0
            if (discount > 0) {
                val old = (plan.price / (1 - discount / 100.0)).toInt()
                binding.oldPrice.visibility = View.VISIBLE
                binding.oldPrice.text = "$old ₽"
                binding.oldPrice.paintFlags =
                    binding.oldPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                binding.discount.visibility = View.VISIBLE
                binding.discount.text = "${discount}%"
            } else {
                binding.oldPrice.visibility = View.GONE
                binding.discount.visibility = View.GONE
            }

            binding.advantagesContainer.removeAllViews()
            val context = binding.root.context
            val durationText = formatDuration(plan.duration_days)
            listOf(durationText, "Фильтр", "Доставка", "Установка", "Обслуживание").forEach { adv ->
                val tv = TextView(context).apply {
                    text = adv
                    textAlignment = View.TEXT_ALIGNMENT_CENTER
                }
                binding.advantagesContainer.addView(tv)
            }

            binding.selectButton.setOnClickListener {
                onSelect(plan)
            }
        }

        private fun formatDuration(days: Int): String = when (days) {
            365 -> "1 год"
            180 -> "6 мес"
            30 -> "1 мес"
            else -> "$days дн"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubscriptionViewHolder {
        val binding = ItemSubscriptionBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SubscriptionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubscriptionViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun submitList(plans: List<DbSubscription>) {
        items = plans
        notifyDataSetChanged()
    }
}

