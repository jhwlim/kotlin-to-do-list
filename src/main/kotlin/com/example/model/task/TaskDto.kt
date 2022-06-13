package com.example.model.task

import com.example.common.enums.TaskStatus
import com.example.domain.category.Category
import com.example.domain.task.Task
import com.example.model.category.CategoryDto
import java.time.LocalDateTime

data class TaskDto(
    val id: Long? = null,
    val name: String,
    val description: String = "",
    val status: TaskStatus,
    val createdDtm: LocalDateTime? = null,
    val modifiedDtm: LocalDateTime? = null,
    val category: CategoryDto? = null,
) {

    companion object {
        fun of(task: Task, category: Category): TaskDto {
            return TaskDto(
                id = task.id,
                name = task.name,
                description = task.description,
                status = task.status,
                createdDtm = task.createdDtm,
                modifiedDtm = task.modifiedDtm,
                category = CategoryDto.from(category),
            )
        }
    }

}
