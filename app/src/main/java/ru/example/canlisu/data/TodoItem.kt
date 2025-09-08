package ru.example.canlisu.data

import kotlinx.serialization.Serializable

@Serializable

data class TodoItem(val id: Int, val name: String)
