package com.example.controller.task.request

import com.example.common.enums.TaskStatus
import com.example.model.category.CategoryDto
import com.example.model.task.TaskDto
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class TaskSaveRequest(
    @field:NotBlank
    val name: String? = null,
    val description: String? = null,
    @field:NotNull
    val status: TaskStatus? = null,
    @field:Valid
    val category: CategorySaveRequest? = null,
) {

    fun toTask(): TaskDto {
        return TaskDto(
            name = name!!,
            description = description ?: "",
            status = status!!,
            category = category!!.toCategory(),
        )
    }

    data class CategorySaveRequest(
        @field:NotBlank
        val name: String? = null,
    ) {

        fun toCategory(): CategoryDto {
            return CategoryDto(name = name!!)
        }

    }

}
