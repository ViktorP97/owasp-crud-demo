package org.example.owaspcruddemo.controller

import org.example.owaspcruddemo.dto.CreateItemDto
import org.example.owaspcruddemo.dto.UpdateItemDto
import jakarta.validation.Valid
import org.example.owaspcruddemo.model.Item
import org.example.owaspcruddemo.model.SearchCriteria
import org.example.owaspcruddemo.service.ItemService
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.OffsetDateTime
import java.util.UUID

@RestController
@RequestMapping("/api/items")
@Validated
class ItemController(
    private val itemService: ItemService
) {

    @PostMapping
    fun createItem(@Valid @RequestBody itemDto: CreateItemDto): ResponseEntity<Item> {
        //OWASP: použite DTO
        //OWASP: Validujte vstupy na strane servera
        //OWASP: autentizácia/autorizácia
        val item = Item.create(itemDto.name, itemDto.description, itemDto.category)
        val savedItem = itemService.save(item)
        return ResponseEntity.ok(savedItem)
    }

    @GetMapping("/{id}")
    fun getItem(@PathVariable id: UUID): ResponseEntity<Item> {
        // OWASP: Overte vstup na strane servera (ID môže byť manipulované)
        //OWASP: autentizácia/autorizácia
        val item = itemService.findById(id) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(item)
    }

    @GetMapping
    fun getAllItems(): List<Item> {
        // OWASP: limitovanie počtu vŕatených items
        //OWASP: autentizácia/autorizácia
        return itemService.findAll()
    }

    @PutMapping("/{id}")
    fun updateItem(
        @PathVariable id: UUID,
        @Valid @RequestBody updateDto: UpdateItemDto
    ): ResponseEntity<Item> {
        val existingItem = itemService.findById(id)

        return if (existingItem != null) {
            val updatedItem = existingItem.copy(
                name = updateDto.name ?: existingItem.name,
                description = updateDto.description ?: existingItem.description,
                category = updateDto.category ?: existingItem.category
            )
            val savedItem = itemService.save(updatedItem)
            ResponseEntity.ok(savedItem)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteItem(@PathVariable id: UUID): ResponseEntity<Void> {
        val item = itemService.findById(id) ?: return ResponseEntity.notFound().build()

        itemService.delete(item.id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/search")
    fun searchItems(
        @RequestParam name: String? = null,
        @RequestParam after: String? = null,
        @RequestParam before: String? = null
    ): ResponseEntity<List<Item>> {
        val searchCriteria = SearchCriteria(
            name = name?.takeIf { it.isNotBlank() },
            after = after?.takeIf { it.isNotBlank() }?.let { OffsetDateTime.parse(it).toInstant() },
            before = before?.takeIf { it.isNotBlank() }?.let { OffsetDateTime.parse(it).toInstant() }
        )

        val results = itemService.search(searchCriteria)

        return ResponseEntity.ok(results)
    }
}