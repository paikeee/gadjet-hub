package ru.spbstu.gadget_hub.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.spbstu.gadget_hub.model.entities.Order
import ru.spbstu.gadget_hub.model.entities.User
import ru.spbstu.gadget_hub.repositories.OrderRepository
import java.time.LocalDate

@Service
class OrderService(
        val orderRepository: OrderRepository,
) {

    @Transactional
    fun createOrder(user: User, price: Long, count: Int) : Int =
            orderRepository.save(
                    count = count,
                    price = price,
                    date = LocalDate.now(),
                    userId = user.id,
            ).let { orderRepository.getLastInsertedOrderId() }

    fun getOrders(user: User) : List<Order> = orderRepository.getAllByUser(user.id)
}