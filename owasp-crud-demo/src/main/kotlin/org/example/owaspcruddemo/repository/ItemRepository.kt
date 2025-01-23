package org.example.owaspcruddemo.repository

import org.example.owaspcruddemo.model.Item
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.UUID

interface ItemRepository : MongoRepository<Item, UUID>
