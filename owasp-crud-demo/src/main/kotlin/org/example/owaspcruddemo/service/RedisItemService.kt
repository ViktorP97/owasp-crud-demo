package org.example.owaspcruddemo.service

import org.example.owaspcruddemo.model.Item
import org.example.owaspcruddemo.model.SearchCriteria
import org.springframework.context.annotation.Profile
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ValueOperations
import org.springframework.stereotype.Component
import java.util.UUID

@Profile("redis")
@Component
class RedisItemService(
    private val redisTemplate: RedisTemplate<String, Item>
) : ItemService {

    private val ops: ValueOperations<String, Item> = redisTemplate.opsForValue()

    override fun save(item: Item): Item {
        ops.set(item.id.toString(), item)
        return item
    }

    override fun findById(id: UUID): Item? {
        return ops.get(id.toString())
    }

    override fun findAll(): List<Item> {
        val keys = redisTemplate.keys("*")
        return keys.mapNotNull { ops.get(it) }
    }

    override fun delete(id: UUID) {
        redisTemplate.delete(id.toString())
    }

    override fun search(criteria: SearchCriteria): List<Item> {
        throw UnsupportedOperationException("Search in Redis is not supported")
    }
}