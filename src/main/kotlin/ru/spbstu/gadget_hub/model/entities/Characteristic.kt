package ru.spbstu.gadget_hub.model.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
@Table(name = "characteristics")
data class Characteristic(
        @JsonIgnore
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Int,
        val name: String,
        val value: String,
        @JsonIgnore
        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "product_id")
        val product: Product,
)
