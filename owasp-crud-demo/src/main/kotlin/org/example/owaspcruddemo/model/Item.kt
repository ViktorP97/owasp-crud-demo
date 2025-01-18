package org.example.owaspcruddemo.model

import org.springframework.data.annotation.Id
import java.util.UUID

data class Item(
    @Id
    val id: UUID,
    val name: String,
    val description: String,
    val category: String
)