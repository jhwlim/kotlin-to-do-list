package com.example.domain.task.projections

import com.example.common.enums.TaskStatus
import com.example.domain.category.Category

interface TaskListItemProjection {

    fun getId(): Long
    fun getName(): String
    fun getStatus(): TaskStatus
    fun getCategory(): Category

}
