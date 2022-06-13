package com.example.controller.task

import com.example.controller.task.request.TaskSaveRequest
import com.example.controller.task.response.TaskListResponse
import com.example.model.task.TaskDto
import com.example.service.TaskService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/tasks")
class TaskController(
    private val taskService: TaskService,
) {

    companion object {
        private val log = LoggerFactory.getLogger(TaskController.Companion::class.java)
    }

    @GetMapping
    fun getTasks(): TaskListResponse {
        return TaskListResponse.from(taskService.getTask())
    }

    @GetMapping("/{taskId}")
    fun getTaskById(@PathVariable taskId: Long): TaskDto {
        return taskService.getTaskById(taskId)
    }

    @PostMapping
    fun saveTask(@RequestBody @Valid request: TaskSaveRequest): ResponseEntity<TaskDto> {
        log.info("[Request] task save request : {}", request)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(taskService.saveTask(request.toTask()))
    }

    @PutMapping("/{taskId}")
    fun updateTask(
        @PathVariable taskId: Long,
        @RequestBody @Valid request: TaskSaveRequest
    ): TaskDto {
        log.info("[Request] task update request : {}", request)
        return taskService.updateTask(taskId, request.toTask())
    }

    @DeleteMapping("/{taskId}")
    fun removeTask(
        @PathVariable taskId: Long
    ): ResponseEntity<Unit> {
        log.info("[Request] task delete request - taskId : {}", taskId)
        taskService.removeTask(taskId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
            .build()
    }

}
