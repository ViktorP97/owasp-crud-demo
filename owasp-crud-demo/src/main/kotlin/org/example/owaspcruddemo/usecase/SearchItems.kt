package org.example.owaspcruddemo.usecase

import org.example.owaspcruddemo.model.Item
import org.example.owaspcruddemo.model.SearchCriteria
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Component


@Component
class SearchItems(
    private val mongoTemplate: MongoTemplate
) {
    fun execute(criteria: SearchCriteria): List<Item> {
        val query = Query()

        criteria.name?.let {
            query.addCriteria(Criteria.where("name").regex(it, "i"))
        }

        val timeCriteria = mutableListOf<Criteria>()
        criteria.after?.let { timeCriteria.add(Criteria.where("time").gte(it)) }
        criteria.before?.let { timeCriteria.add(Criteria.where("time").lte(it)) }

        if (timeCriteria.isNotEmpty()) {
            query.addCriteria(Criteria().andOperator(*timeCriteria.toTypedArray()))
        }

        return mongoTemplate.find(query, Item::class.java)
    }
}