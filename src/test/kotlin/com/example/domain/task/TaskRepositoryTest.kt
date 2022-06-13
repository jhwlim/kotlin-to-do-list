package com.example.domain.task

import com.example.common.enums.TaskStatus
import com.example.domain.category.Category
import com.example.domain.category.CategoryRepository
import com.example.domain.task.projections.TaskListItemProjection
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.jdbc.Sql
import java.time.LocalDateTime

@DataJpaTest
internal class TaskRepositoryTest {

    @Autowired
    lateinit var categoryRepository: CategoryRepository
    @Autowired
    lateinit var taskRepository: TaskRepository

    @DisplayName("Task 등록")
    @Test
    fun save() {
        val category = categoryRepository.save(Category(name = "카테고리"))
        val task = Task(
            name = "Task",
            status = TaskStatus.PLANNING,
            createdDtm = LocalDateTime.now(),
            modifiedDtm = LocalDateTime.now(),
        )
        category.addTasks(task)
        val savedTask = taskRepository.save(task)

        assertThat(savedTask.id).isNotNull
    }

    @DisplayName("Task 조회")
    @Test
    @Sql("/sql/tasks.sql")
    fun findByIdOrNull() {
        var actualTask = taskRepository.findByIdOrNull(1L)
        assertThat(actualTask).isNotNull

        actualTask?.category.let { category ->
            assertThat(category).isNotNull
            assertThat(category!!.name).isEqualTo("카테고리1")
        }
    }

    @DisplayName("Task 목록 조회 - TaskListItemProjection")
    @Test
    @Sql("/sql/tasks.sql")
    fun findAll_whenTaskListItemProjection() {
        val list = taskRepository.findAllByOrderByCreatedDtmDesc(TaskListItemProjection::class.java)
        assertThat(list.size).isEqualTo(2)
        assertThat(list[0].getId()).isEqualTo(2L)
        assertThat(list[1].getId()).isEqualTo(1L)
    }

}
