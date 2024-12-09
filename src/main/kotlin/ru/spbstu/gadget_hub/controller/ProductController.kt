package ru.spbstu.gadget_hub.controller

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import ru.spbstu.gadget_hub.model.dto.*
import ru.spbstu.gadget_hub.service.ProductService

@RestController
class ProductController(
        val productService: ProductService
) {
    @GetMapping(path = ["/product/{id}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getProduct(@PathVariable("id") id: Int) : ProductResponse? =
            productService.getProduct(id)?.build()

    @PostMapping(
            path = ["/goods"],
            produces = [MediaType.APPLICATION_JSON_VALUE],
            consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun getProducts(@RequestBody request: ProductRequest) : PageResponse =
            productService.getProducts(
                    type = request.type,
                    color = request.color,
                    from = request.from,
                    to = request.to,
                    sortBy = request.sortBy,
                    limit = request.limit,
                    offset = request.offset
            )

    @GetMapping(path = ["/goods/{tag}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getProduct(@PathVariable("tag") tag: String) : List<ProductResponse> =
            productService.getProducts(tag).map { it.build() }
}