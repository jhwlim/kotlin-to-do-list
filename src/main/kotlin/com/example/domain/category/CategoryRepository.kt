package com.example.domain.category

import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<Category, Long> {

    fun findByName(name: String): Category?

}

