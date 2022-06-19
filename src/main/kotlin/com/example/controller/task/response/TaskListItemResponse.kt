package com.example.controller.task.response

import com.example.common.enums.TaskStatus
import com.example.model.category.CategoryDto
import com.example.model.task.TaskDto

data class TaskListItemResponse(
    val id: Long,
    val name: String,
    val status: TaskStatus,
    val category: TaskListItemCategoryResponse,
) {

    companion object {

        fun list(tasks: List<TaskDto>): List<TaskListItemResponse> {
            return tasks.map{ task -> from(task) }
        }

        private fun from(task: TaskDto): TaskListItemResponse {
            return TaskListItemResponse(
                id = task.id!!,
                name = task.name,
                status = task.status,
                category = TaskListItemCategoryResponse.from(task.category!!)
            )
        }

    }

    data class TaskListItemCategoryResponse(
        val id: Long,
        val name: String,
    ) {

        companion object {

            fun from(category: CategoryDto): TaskListItemCategoryResponse {
                return TaskListItemCategoryResponse(
                    id = category.id!!,
                    name = category.name,
                )
            }

        }

    }

}
