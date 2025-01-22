package org.example.owaspcruddemo.model

import org.springframework.data.annotation.Id
import java.time.Instant
import java.util.UUID

data class Item(
    @Id
    val id: UUID,
    val name: String,
    val description: String,
    val category: String,
    val time: Instant
) {
    companion object {
        fun create(
            name: String,
            description: String,
            category: String,
        ) = Item(
            UUID.randomUUID(),
            name,
            description,
            category,
            Instant.now()
        )
    }
}