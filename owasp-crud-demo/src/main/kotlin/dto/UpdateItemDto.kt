package dto

import jakarta.validation.constraints.Size

data class UpdateItemDto(
    @field:Size(max = 50)
    val name: String? = null,

    @field:Size(max = 100)
    val description: String? = null,

    @field:Size(max = 100)
    val category: String? = null
)