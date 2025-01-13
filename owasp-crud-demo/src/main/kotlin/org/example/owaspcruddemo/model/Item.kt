package org.example.owaspcruddemo.model

import org.springframework.data.annotation.Id

data class Item(
    @Id
    val id: String,
    val name: String,
    val description: String,
    val category: String
)