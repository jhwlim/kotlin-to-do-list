package com.example.domain.category

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.jdbc.Sql

@DataJpaTest
internal class CategoryRepositoryTest {

    @Autowired
    lateinit var categoryRepository: CategoryRepository

    @DisplayName("Category 등록 - 성공")
    @Test
    fun save() {
        val category = Category(name = "카테고리")

        val savedCategory = categoryRepository.save(category)

        assertThat(savedCategory.id).isNotNull
    }

    @DisplayName("Task 목록 조회 from Category")
    @Test
    @Sql("/sql/tasks.sql")
    fun findByIdOrNull() {
        val category = categoryRepository.findByIdOrNull(1L)
        assertThat(category).isNotNull

        val tasks = category?.tasks ?: mutableSetOf()
        assertThat(tasks.isNotEmpty()).isTrue
    }

    @DisplayName("Category 조회 by 이름 - 성공")
    @Test
    @Sql("/sql/tasks.sql")
    fun findByName_whenCategoryIsFound() {
        val category = categoryRepository.findByName("카테고리1")

        assertThat(category).isNotNull
    }

    @DisplayName("Category 조회 by 이름 - 카테고리를 조회할 수 없다면 null을 반환한다.")
    @Test
    @Sql("/sql/tasks.sql")
    fun findByName_whenCategoryIsNotFound() {
        val category = categoryRepository.findByName("카테고리")

        assertThat(category).isNull()
    }


}
