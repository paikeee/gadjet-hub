package ru.spbstu.gadget_hub.model.dto

data class PageResponse(
        val products: List<ProductResponse>,
        val total: Int,
        val limit: Int,
        val offset: Int,
)
