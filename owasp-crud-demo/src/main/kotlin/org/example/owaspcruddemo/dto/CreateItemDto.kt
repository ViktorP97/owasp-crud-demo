package org.example.owaspcruddemo.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.example.owaspcruddemo.model.Item
import java.util.UUID

data class CreateItemDto(
    //OWASP: validácia dĺžky
    @field:NotBlank
    @field:Size(max = 50)
    val name: String,
    @field:NotBlank
    @field:Size(max = 100)
    val description: String,
    @field:NotBlank
    @field:Size(max = 100)
    val category: String
) {
    fun toDomain(): Item {
        return Item(
            id = UUID.randomUUID(),
            name = name,
            description = description,
            category = category
        )
    }
}