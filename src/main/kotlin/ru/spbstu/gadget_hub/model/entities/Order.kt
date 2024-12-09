package ru.spbstu.gadget_hub.model.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "order")
data class Order(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Int,
        val count: Int,
        val price: Long,
        val date: LocalDate,
        @JsonIgnore
        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "user_id")
        val user: User,
)
