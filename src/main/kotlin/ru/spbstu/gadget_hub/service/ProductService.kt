package ru.spbstu.gadget_hub.service

import org.springframework.stereotype.Service
import ru.spbstu.gadget_hub.model.dto.PageResponse
import ru.spbstu.gadget_hub.model.dto.ProductResponse
import ru.spbstu.gadget_hub.model.dto.build
import ru.spbstu.gadget_hub.model.dto.paged
import ru.spbstu.gadget_hub.model.entities.Product
import ru.spbstu.gadget_hub.model.entities.Sort
import ru.spbstu.gadget_hub.model.entities.Tags
import ru.spbstu.gadget_hub.repositories.ProductRepository

@Service
class ProductService(
        val productRepository: ProductRepository,
) {

    fun getProduct(id: Int) : Product? {
        return productRepository.getProductById(id)
    }

    fun getProducts(
            type: List<String>?,
            color: List<String>?,
            from: Int?,
            to: Int?,
            sortBy: String?,
            limit: Int,
            offset: Int
    ): PageResponse {
        var total = 0
        return productRepository.getProducts()
                .asSequence()
                .filter { product ->
                    (type == null || type.contains(product.category)) &&
                    (color == null || color.contains(product.color)) &&
                    (product.price in (from ?: 0)..(to ?: Integer.MAX_VALUE))
                }
                .let{ list -> sortBy?.let { list.sort(sortBy) } ?: list}
                .also { total = it.count() }
                .drop(offset)
                .take(limit)
                .toList().map { it.build() }
                .let { PageResponse(it, total, limit, offset) }
    }

    fun getProducts(tag: String): List<Product> =
            productRepository.getProducts()
                    .filter {
                        (it.tags ?: emptyList())
                                .map { tag -> tag.name }.contains(tag)
                    }

    private fun Sequence<Product>.sort(sortBy: String) : Sequence<Product> {
        return when (Sort.fromString(sortBy)) {
            Sort.CHEAP -> this.sortedBy { it.price }
            Sort.EXPENSIVE -> this.sortedByDescending { it.price }
            Sort.HIT -> this.sortedWith(
                    compareByDescending {
                        it.tags?.map{ tag -> tag.name }?.contains(Tags.HIT.value)
                    })
            Sort.NEW -> this.sortedWith(
                    compareByDescending {
                        it.tags?.map{ tag -> tag.name }?.contains(Tags.NEW.value)
                    })
        }
    }
}