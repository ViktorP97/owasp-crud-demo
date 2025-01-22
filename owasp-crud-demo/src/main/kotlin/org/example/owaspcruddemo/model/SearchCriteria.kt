package org.example.owaspcruddemo.model

import java.time.Instant

data class SearchCriteria(
    val name: String?,
    val after: Instant?,
    val before: Instant?
)