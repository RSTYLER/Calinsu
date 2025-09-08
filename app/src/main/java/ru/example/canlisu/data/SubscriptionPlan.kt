package ru.example.canlisu.data

/**
 * Model describing a subscription plan option displayed on the subscriptions screen.
 */
data class SubscriptionPlan(
    val title: String,
    val price: String,
    val oldPrice: String? = null,
    val discountPercent: Int? = null,
    val advantages: List<String>
)
