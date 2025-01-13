package org.example.owaspcruddemo.repository

import org.example.owaspcruddemo.model.Item
import org.springframework.data.mongodb.repository.MongoRepository

interface ItemRepository : MongoRepository<Item, String>