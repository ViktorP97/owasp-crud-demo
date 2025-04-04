package org.example.owaspcruddemo.service

import org.example.owaspcruddemo.model.Item
import org.example.owaspcruddemo.model.SearchCriteria
import java.util.UUID

interface ItemService {
    fun save(item: Item): Item
    fun findById(id: UUID): Item?
    fun findAll(): List<Item>
    fun delete(id: UUID)
    fun search(criteria: SearchCriteria): List<Item>
}