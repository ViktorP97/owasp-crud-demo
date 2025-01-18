package org.example.owaspcruddemo.controller

import org.example.owaspcruddemo.dto.CreateItemDto
import org.example.owaspcruddemo.dto.UpdateItemDto
import jakarta.validation.Valid
import org.example.owaspcruddemo.model.Item
import org.example.owaspcruddemo.repository.ItemRepository
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/items")
@Validated
class ItemController(
    private val itemRepository: ItemRepository
) {

    @PostMapping
    fun createItem(@Valid @RequestBody itemDto: CreateItemDto): ResponseEntity<Item> {
        //OWASP: použite DTO
        //OWASP: Validujte vstupy na strane servera
        //OWASP: autentizácia/autorizácia
        val item = itemDto.toDomain()
        val savedItem = itemRepository.save(item)
        return ResponseEntity.ok(savedItem)
    }

    @GetMapping("/{id}")
    fun getItem(@PathVariable id: UUID): ResponseEntity<Item> {
        // OWASP: Overte vstup na strane servera (ID môže byť manipulované)
        //OWASP: autentizácia/autorizácia
        val item = itemRepository.findById(id)
        return if (item.isPresent) ResponseEntity.ok(item.get()) else ResponseEntity.notFound().build()
    }

    @GetMapping
    fun getAllItems(): List<Item> {
        // OWASP: limitovanie počtu vŕatených items
        //OWASP: autentizácia/autorizácia
        return itemRepository.findAll()
    }

    @PutMapping("/{id}")
    fun updateItem(
        @PathVariable id: UUID,
        @Valid @RequestBody updateDto: UpdateItemDto
    ): ResponseEntity<Item> {
        // OWASP: Skontroluj, či existuje záznam s daným ID
        //OWASP: autentizácia/autorizácia
        val existingItem = itemRepository.findById(id)

        if (updateDto.name?.isBlank() == true
            || updateDto.description?.isBlank() == true
            || updateDto.category?.isBlank() == true
            ) {
            return ResponseEntity.badRequest().body(null)
        }

        return if (existingItem.isPresent) {
            val updatedItem = existingItem.get().copy(
                name = updateDto.name ?: existingItem.get().name,
                description = updateDto.description ?: existingItem.get().description,
                category = updateDto.category ?: existingItem.get().category
            )

            ResponseEntity.ok(itemRepository.save(updatedItem))
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteItem(@PathVariable id: UUID): ResponseEntity<Void> {
        val item = itemRepository.findById(id)
        if (!item.isPresent) {
            return ResponseEntity.notFound().build()
        }
        itemRepository.deleteById(id)
        return ResponseEntity.noContent().build()
    }
}