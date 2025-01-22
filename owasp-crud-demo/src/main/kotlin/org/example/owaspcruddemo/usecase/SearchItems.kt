package org.example.owaspcruddemo.usecase

import org.example.owaspcruddemo.model.Item
import org.example.owaspcruddemo.model.SearchCriteria
import org.example.owaspcruddemo.repository.ItemRepository
import org.springframework.stereotype.Component

@Component
class SearchItems(
    private val itemRepository: ItemRepository
) {
     fun execute(criteria: SearchCriteria): List<Item> {
        return when {
            criteria.name != null && criteria.after != null && criteria.before != null ->
                itemRepository.findByNameContainsIgnoreCaseAndTimeBetween(criteria.name, criteria.after, criteria.before)

            criteria.name != null && criteria.after != null ->
                itemRepository.findByNameContainsIgnoreCaseAndTimeAfter(criteria.name, criteria.after)

            criteria.name != null && criteria.before != null ->
                itemRepository.findByNameContainsIgnoreCaseAndTimeBefore(criteria.name, criteria.before)

            criteria.after != null && criteria.before != null ->
                itemRepository.findByTimeBetween(criteria.after, criteria.before)

            criteria.after != null ->
                itemRepository.findByTimeAfter(criteria.after)

            criteria.before != null ->
                itemRepository.findByTimeBefore(criteria.before)

            criteria.name != null ->
                itemRepository.findByNameContainsIgnoreCase(criteria.name)

            else -> itemRepository.findAll()
        }
    }
}