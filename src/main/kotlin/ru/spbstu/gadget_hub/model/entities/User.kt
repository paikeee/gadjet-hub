package ru.spbstu.gadget_hub.model.entities

import jakarta.persistence.*
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "users")
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Int,
        val login: String,
        val password: String,
        @OneToMany(cascade = [(CascadeType.ALL)], fetch = FetchType.LAZY, mappedBy = "user")
        val orders: MutableList<Order> = ArrayList(),
)
