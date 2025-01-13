package org.example.owaspcruddemo.controller

import org.example.owaspcruddemo.model.Item
import org.example.owaspcruddemo.repository.ItemRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/items")
class ItemController(
    private val itemRepository: ItemRepository
) {

    @PostMapping
    fun createItem(@RequestBody item: Item): ResponseEntity<Item> {
        // OWASP: Validujte vstupy na strane servera (SQL/NoSQL Injection)
        if (item.name.isBlank() || item.description.isBlank()) {
            return ResponseEntity.badRequest().build()
        }
        val savedItem = itemRepository.save(item)
        return ResponseEntity.ok(savedItem)
    }

    @GetMapping("/{id}")
    fun getItem(@PathVariable id: String): ResponseEntity<Item> {
        // OWASP: Overte vstup na strane servera (ID môže byť manipulované)
        val item = itemRepository.findById(id)
        return if (item.isPresent) ResponseEntity.ok(item.get()) else ResponseEntity.notFound().build()
    }

    @GetMapping
    fun getAllItems(): List<Item> {
        // OWASP: Limitujte počet vrátených záznamov
        return itemRepository.findAll()
    }

    @PutMapping("/{id}")
    fun updateItem(@PathVariable id: String, @RequestBody item: Item): ResponseEntity<Item> {
        //OWASP: zistit ci existuje este pred aktualizaciou
        val existingItem = itemRepository.findById(id)
        return if (existingItem.isPresent) {
            val updatedItem = itemRepository.save(existingItem.get().copy(name = item.name, description = item.description))
            ResponseEntity.ok(updatedItem)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteItem(@PathVariable id: String): ResponseEntity<Void> {
        //OWASP: autentizáciou/autorizáciou
        itemRepository.deleteById(id)
        return ResponseEntity.noContent().build()
    }
}