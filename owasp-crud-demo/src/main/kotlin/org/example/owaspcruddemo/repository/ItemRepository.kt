package org.example.owaspcruddemo.repository

import org.example.owaspcruddemo.model.Item
import org.springframework.data.mongodb.repository.MongoRepository
import java.time.Instant
import java.util.UUID

interface ItemRepository : MongoRepository<Item, UUID> {
    fun findByNameContainsIgnoreCase(name: String): List<Item>
    fun findByNameContainsIgnoreCaseAndTimeBetween(name: String, time: Instant, time2: Instant): List<Item>
    fun findByNameContainsIgnoreCaseAndTimeAfter(name: String, time: Instant): List<Item>
    fun findByNameContainsIgnoreCaseAndTimeBefore(name: String, time: Instant): List<Item>
    fun findByTimeBetween(time: Instant, time2: Instant): List<Item>
    fun findByTimeAfter(time: Instant): List<Item>
    fun findByTimeBefore(time: Instant): List<Item>
}