package ru.spbstu.gadget_hub.model.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
@Table(name = "tag")
data class Tag(
        @JsonIgnore
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Int,
        val name: String,
        @JsonIgnore
        @ManyToMany
        @JoinTable(
                name = "tag",
                joinColumns = [JoinColumn(name = "id")],
                inverseJoinColumns = [JoinColumn(name = "product_id")]
        )
        val products: MutableList<Product> = ArrayList(),
)
