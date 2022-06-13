package com.example.service

import com.example.domain.category.Category
import com.example.domain.category.CategoryRepository
import com.example.domain.task.Task
import com.example.domain.task.TaskRepository
import com.example.domain.task.projections.TaskListItemProjection
import com.example.exception.TaskNotFoundException
import com.example.model.task.TaskDto
import org.slf4j.LoggerFactory
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

    companion object {

        val log = LoggerFactory.getLogger(TaskService::class.java)

    }

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
        val task = getTaskByIdOrThrow(id)
        return TaskDto.of(task, task.category!!)
    }

    fun getTaskByIdOrThrow(id: Long): Task {
        return taskRepository.findByIdOrNull(id) ?: throw TaskNotFoundException()
    }

    @Transactional
    fun updateTask(taskId: Long, request: TaskDto): TaskDto {
        val savedTask = getTaskByIdOrThrow(taskId)
        savedTask.update(request)
        val updatedTask = taskRepository.save(savedTask)

        val categoryNameRequested = request.category!!.name
        if (categoryNameRequested != updatedTask.category!!.name) {
            savedTask.category!!.removeTask(savedTask)

            val newCategory = categoryRepository.findByName(categoryNameRequested)
                ?: categoryRepository.save(Category(name = categoryNameRequested))
            newCategory.addTasks(savedTask)
        }
        log.info("[Success] Update Task")
        return TaskDto.of(updatedTask, updatedTask.category!!)
    }

}
