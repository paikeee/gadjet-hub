package ru.spbstu.gadget_hub.model.dto

import ru.spbstu.gadget_hub.model.entities.Product

data class ProductResponse(
        val id: Int,
        val name: String,
        val desc: String,
        val price: Int,
        val category: String,
        val img: String,
        val rating: Int,
        val color: String,
        val tags: List<String>?,
        val characteristic: List<String>?,
)

fun Product.build(): ProductResponse {
    return ProductResponse(
            id = this.id,
            name = this.name,
            desc = this.desc,
            price = this.price,
            category = this.category,
            img = this.img,
            rating = this.rating,
            color = this.color,
            tags = this.tags?.map { it.name } ?: emptyList(),
            characteristic = this.characteristics?.map { it.name + ": " + it.value} ?: emptyList(),
    )
}
