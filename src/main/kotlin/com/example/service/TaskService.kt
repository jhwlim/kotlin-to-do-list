package com.example.service

import com.example.domain.category.Category
import com.example.domain.category.CategoryRepository
import com.example.domain.task.Task
import com.example.domain.task.TaskRepository
import com.example.domain.task.projections.TaskListItemProjection
import com.example.exception.TaskNotFoundException
import com.example.model.task.TaskDto
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Transactional(readOnly = true)
@Service
class TaskService(
    private val categoryRepository: CategoryRepository,
    private val taskRepository: TaskRepository,
) {

    @Transactional
    fun saveTask(request: TaskDto): TaskDto {
        val savedCategory = categoryRepository.findByName(request.category!!.name)
            ?: categoryRepository.save(Category(name = request.category.name))
        val savedTask = taskRepository.save(Task(
            name = request.name,
            description = request.description,
            status = request.status,
            createdDtm = LocalDateTime.now(),
        ))
        savedCategory.addTasks(savedTask)
        return TaskDto.of(savedTask, savedCategory)
    }

    fun getTask(): List<TaskDto> {
        return TaskDto.list(taskRepository.findAllByOrderByCreatedDtmDesc(TaskListItemProjection::class.java))
    }

    fun getTaskById(id: Long): TaskDto {
        val task = taskRepository.findByIdOrNull(id) ?: throw TaskNotFoundException()
        return TaskDto.of(task, task.category!!)
    }

}
