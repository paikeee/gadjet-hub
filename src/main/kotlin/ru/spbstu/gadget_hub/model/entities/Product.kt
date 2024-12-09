package ru.spbstu.gadget_hub.model.entities

import jakarta.persistence.*

@Entity
@Table(name = "product")
data class Product(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Int,
        val name: String,
        val desc: String,
        val price: Int,
        val category: String,
        val img: String,
        val rating: Int,
        val color: String,
        @OneToMany(cascade = [(CascadeType.ALL)], fetch = FetchType.LAZY, mappedBy = "product")
        val characteristics: MutableList<Characteristic>? = ArrayList(),
        @ManyToMany(mappedBy = "products")
        val tags: MutableList<Tag>? = ArrayList(),
)
