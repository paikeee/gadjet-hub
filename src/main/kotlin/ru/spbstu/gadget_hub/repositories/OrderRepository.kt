package ru.spbstu.gadget_hub.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import ru.spbstu.gadget_hub.model.entities.Order
import ru.spbstu.gadget_hub.model.entities.User
import java.time.LocalDate

@Repository
interface OrderRepository : JpaRepository<Order, Int> {

    @Modifying
    @Query(value = """INSERT INTO "order" (count, date, price, user_id) 
                  VALUES (:count, :date, :price, :userId)""", nativeQuery = true)
    fun save(
            @Param("count") count: Int,
            @Param("date") date: LocalDate,
            @Param("price") price: Long,
            @Param("userId") userId: Int,
            ) : Int

    @Query(value = """SELECT currval(pg_get_serial_sequence('"order"', 'id'))""", nativeQuery = true)
    fun getLastInsertedOrderId(): Int

    @Query(value = """SELECT * FROM "order" WHERE user_id = :userId""", nativeQuery = true)
    fun getAllByUser(@Param("userId") userId: Int): List<Order>
}