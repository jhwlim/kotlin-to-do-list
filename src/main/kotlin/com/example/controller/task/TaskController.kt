package com.example.controller.task

import com.example.controller.task.request.TaskSaveRequest
import com.example.model.task.TaskDto
import com.example.service.TaskService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/tasks")
class TaskController(
    private val taskService: TaskService,
) {

    companion object {
        private val log = LoggerFactory.getLogger(TaskController.Companion::class.java)
    }

    @PostMapping
    fun saveTask(@RequestBody @Valid request: TaskSaveRequest): ResponseEntity<TaskDto> {
        log.info("task save request : {}", request)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(taskService.saveTask(request.toTask()))
    }

}
