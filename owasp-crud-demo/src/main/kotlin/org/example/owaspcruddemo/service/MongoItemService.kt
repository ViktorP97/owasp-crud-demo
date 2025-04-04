package org.example.owaspcruddemo.service

import org.example.owaspcruddemo.model.Item
import org.example.owaspcruddemo.model.SearchCriteria
import org.example.owaspcruddemo.repository.ItemMongoRepository
import org.example.owaspcruddemo.usecase.SearchItems
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.util.UUID

@Profile("mongo")
@Component
class MongoItemService(
    private val repository: ItemMongoRepository,
    private val searchItems: SearchItems
) : ItemService {

    override fun save(item: Item): Item {
        repository.save(item)
        return item
    }

    override fun findById(id: UUID): Item? {
        return repository.findById(id).orElse(null)
    }

    override fun findAll(): List<Item> {
        return repository.findAll()
    }

    override fun delete(id: UUID) {
        repository.deleteById(id)
    }

    override fun search(criteria: SearchCriteria): List<Item> {
        return searchItems.executeMongo(criteria)
    }
}