package ru.spbstu.gadget_hub.controller

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.spbstu.gadget_hub.model.entities.Order
import ru.spbstu.gadget_hub.service.JwtUserDetailsService
import ru.spbstu.gadget_hub.service.OrderService
import ru.spbstu.gadget_hub.service.TokenService

@RestController
class OrderController(
        val orderService: OrderService,
        val tokenService: TokenService,
        val jwtUserDetailsService: JwtUserDetailsService,
) {
    @GetMapping(path = ["/order"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getOrders(@RequestParam(name = "token") token: String) : List<Order> =
            orderService.getOrders(jwtUserDetailsService.getUser(tokenService.extractUsername(token)))

    @PostMapping(path = ["/order"])
    fun createOrder(
            @RequestParam(name = "price") price: Long,
            @RequestParam(name = "count") count: Int,
            @RequestParam(name = "token") token: String,
    ) : Int =
            orderService.createOrder(
                    jwtUserDetailsService.getUser(tokenService.extractUsername(token)),
                    price,
                    count
            )
}