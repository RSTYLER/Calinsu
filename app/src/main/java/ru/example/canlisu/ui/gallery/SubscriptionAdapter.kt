package ru.example.canlisu.ui.gallery

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.example.canlisu.data.SubscriptionPlan
import ru.example.canlisu.databinding.ItemSubscriptionBinding

class SubscriptionAdapter(
    private var items: List<SubscriptionPlan>,
    private val onSelect: (SubscriptionPlan) -> Unit
) : RecyclerView.Adapter<SubscriptionAdapter.SubscriptionViewHolder>() {

    inner class SubscriptionViewHolder(private val binding: ItemSubscriptionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(plan: SubscriptionPlan) {
            binding.planTitle.text = plan.title
            binding.planPrice.text = plan.price

            if (plan.oldPrice != null) {
                binding.oldPrice.visibility = View.VISIBLE
                binding.oldPrice.text = plan.oldPrice
                binding.oldPrice.paintFlags = binding.oldPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                binding.oldPrice.visibility = View.GONE
            }

            if (plan.discountPercent != null) {
                binding.discount.visibility = View.VISIBLE
                binding.discount.text = "${plan.discountPercent}%"
            } else {
                binding.discount.visibility = View.GONE
            }

            binding.advantagesContainer.removeAllViews()
            plan.advantages.forEach { advantage ->
                val tv = TextView(binding.root.context).apply {
                    text = advantage
                    textAlignment = View.TEXT_ALIGNMENT_CENTER
                }
                binding.advantagesContainer.addView(tv)
            }

            binding.selectButton.setOnClickListener {
                onSelect(plan)
            }
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

    fun submitList(plans: List<SubscriptionPlan>) {
        items = plans
        notifyDataSetChanged()
    }
}
