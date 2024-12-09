package ru.spbstu.gadget_hub.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.spbstu.gadget_hub.model.entities.User

@Repository
interface UserRepository : JpaRepository<User, Int> {

    fun findByLogin(login: String) : User?
}