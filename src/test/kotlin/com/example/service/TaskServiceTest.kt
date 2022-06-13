package com.example.service

import com.example.common.enums.TaskStatus
import com.example.domain.category.Category
import com.example.domain.category.CategoryRepository
import com.example.domain.task.Task
import com.example.domain.task.TaskRepository
import com.example.model.category.CategoryDto
import com.example.model.task.TaskDto
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class TaskServiceTest {

    @DisplayName("Task 등록 - 성공")
    @Test
    fun saveTask() {
        val categoryRepository: CategoryRepository = mockk()
        val taskRepository: TaskRepository = mockk()
        val taskService = TaskService(
            categoryRepository = categoryRepository,
            taskRepository = taskRepository
        )

        val task = TaskDto(
            name = "Task",
            status = TaskStatus.PLANNING,
            category = CategoryDto(name = "카테고리")
        )

        val savedCategory = Category(id = 1L, name = task.category!!.name)
        val savedTask = Task(
            id = 1L,
            name = task.name,
            status = task.status,
            createdDtm = LocalDateTime.now(),
            modifiedDtm = LocalDateTime.now(),
        )

        every { categoryRepository.findByName(task.category!!.name) } returns savedCategory
        every { taskRepository.save(any()) } returns savedTask

        val actual = taskService.saveTask(task)

        assertThat(actual.id).isEqualTo(savedTask.id)
        assertThat(actual.name).isEqualTo(savedTask.name)
        assertThat(actual.status).isEqualTo(savedTask.status)
        assertThat(actual.description).isEqualTo(savedTask.description)
        assertThat(actual.createdDtm).isEqualTo(savedTask.createdDtm)
        assertThat(actual.modifiedDtm).isEqualTo(savedTask.modifiedDtm)

        assertThat(actual.category).isNotNull
        assertThat(actual.category!!.id).isEqualTo(savedCategory.id)
        assertThat(actual.category!!.name).isEqualTo(savedCategory.name)
    }

}
