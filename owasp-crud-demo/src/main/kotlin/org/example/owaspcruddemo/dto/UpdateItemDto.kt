package org.example.owaspcruddemo.dto

import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class UpdateItemDto(
    @field:Size(max = 50)
    @field:Pattern(regexp = ".*\\S.*")
    val name: String? = null,

    @field:Size(max = 100)
    @field:Pattern(regexp = ".*\\S.*")
    val description: String? = null,

    @field:Size(max = 100)
    @field:Pattern(regexp = ".*\\S.*")
    val category: String? = null
)