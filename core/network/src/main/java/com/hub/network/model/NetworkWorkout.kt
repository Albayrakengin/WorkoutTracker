package com.hub.network.model

data class NetworkWorkout(
    val id: String = "",
    val userId: String = "",
    val name: String = "",
    val type: String = "",
    val description: String = "",
    val notes: String = "",
    val exercises: List<String> = emptyList(),
    val lastSession: String = "",
    val imageUrl: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
) 