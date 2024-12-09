package ru.spbstu.gadget_hub.model.dto

data class ProductRequest(
        val type: List<String>?,
        val color: List<String>?,
        val from: Int?,
        val to: Int?,
        val sortBy: String?,
        val limit: Int,
        val offset: Int
)
