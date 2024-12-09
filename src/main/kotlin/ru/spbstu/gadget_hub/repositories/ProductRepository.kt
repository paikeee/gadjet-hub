package ru.spbstu.gadget_hub.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.spbstu.gadget_hub.model.entities.Product

@Repository
interface ProductRepository : JpaRepository<Product, Int> {

    fun getProductById(id: Int) : Product?

    @Query(value = "SELECT * FROM product", nativeQuery = true)
    fun getProducts() : List<Product>
}