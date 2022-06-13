package com.example.model.category

import com.example.domain.category.Category

data class CategoryDto(
    val id: Long? = null,
    val name: String,
) {

    companion object {
        fun from(category: Category): CategoryDto {
            return CategoryDto(
                id = category.id,
                name = category.name,
            )
        }
    }

}
