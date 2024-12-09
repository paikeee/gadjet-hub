package ru.spbstu.gadget_hub

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GadgetHubApplication

fun main(args: Array<String>) {
	runApplication<GadgetHubApplication>(*args)
}
