package com.example.service

import com.example.common.enums.TaskStatus
import com.example.domain.category.Category
import com.example.domain.category.CategoryRepository
import com.example.domain.task.Task
import com.example.domain.task.TaskRepository
import com.example.exception.TaskNotFoundException
import com.example.model.category.CategoryDto
import com.example.model.task.TaskDto
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull
import java.time.LocalDateTime

internal class TaskServiceTest {

    private val categoryRepository: CategoryRepository = mockk()
    private val taskRepository: TaskRepository = mockk()

    private val taskService = TaskService(
        categoryRepository = categoryRepository,
        taskRepository = taskRepository,
    )

    @DisplayName("Task 등록 - 성공")
    @Test
    fun saveTask() {
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

    @DisplayName("Task 조회 by ID - 성공")
    @Test
    fun getTaskById() {
        val category = Category(
            id = 1L,
            name = "Category"
        )
        val task = Task(
            id = 1L,
            name = "Task",
            status = TaskStatus.PLANNING,
            createdDtm = LocalDateTime.now(),
            category = category,
        )

        every { taskRepository.findByIdOrNull(task.id) } returns task

        val actual = taskService.getTaskById(task.id!!)
        assertThat(actual.id).isEqualTo(task.id)
        assertThat(actual.name).isEqualTo(task.name)
        assertThat(actual.status).isEqualTo(task.status)
        assertThat(actual.createdDtm).isEqualTo(task.createdDtm)
        assertThat(actual.modifiedDtm).isEqualTo(task.modifiedDtm)

        val actualCategory = actual.category
        assertThat(actualCategory).isNotNull
        assertThat(actualCategory!!.id).isEqualTo(category.id)
        assertThat(actualCategory.name).isEqualTo(category.name)
    }

    @DisplayName("Task 조회 by Id - Task를 찾을 수 없다면 예외가 발생해야 한다.")
    @Test
    fun getTaskById_whenTaskNotFound() {
        val taskId = 1L

        every { taskRepository.findByIdOrNull(taskId) } returns null

        assertThatExceptionOfType(TaskNotFoundException::class.java).isThrownBy { taskService.getTaskById(taskId) }
    }

}
