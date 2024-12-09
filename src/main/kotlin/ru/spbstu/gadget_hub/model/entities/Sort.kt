package ru.spbstu.gadget_hub.model.entities

enum class Sort(val type: String) {
    NEW("new"),
    HIT("hit"),
    CHEAP("cheap"),
    EXPENSIVE("expensive");

    companion object {
        fun fromString(value: String): Sort {
            return values().firstOrNull { it.type.equals(value, ignoreCase = true) }
                    ?: throw IllegalArgumentException("Unknown Sort type: $value")
        }
    }
}