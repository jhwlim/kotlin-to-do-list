package com.example.service

import com.example.domain.category.Category
import com.example.domain.category.CategoryRepository
import com.example.domain.task.Task
import com.example.domain.task.TaskRepository
import com.example.model.task.TaskDto
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

}
