package org.example.owaspcruddemo.model

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.springframework.data.annotation.Id

data class Item(
    @Id
    val id: String? = null,
    @field:Size(max = 50)
    val name: String,
    @field:NotBlank
    @field:Size(max = 100)
    val description: String
)