package com.example.controller.task

import com.example.common.advice.ErrorResponse
import com.example.common.enums.ErrorType
import com.example.common.enums.TaskStatus
import com.example.controller.task.request.CategorySaveRequest
import com.example.controller.task.request.TaskSaveRequest
import com.example.model.category.CategoryDto
import com.example.model.task.TaskDto
import com.example.service.TaskService
import com.fasterxml.jackson.databind.ObjectMapper
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime

@WebMvcTest(TaskController::class)
internal class TaskControllerTest {

    @TestConfiguration
    class TaskControllerTestConfig {
        @Bean
        fun taskService(): TaskService = mockk()
    }

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var taskService: TaskService

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @DisplayName("Task 등록 - 성공")
    @Test
    fun saveTask() {
        val request = TaskSaveRequest(
            name = "Task",
            status = TaskStatus.PLANNING,
            category = CategorySaveRequest(
                name = "Category"
            )
        )
        val savedTask = TaskDto(
            id = 1L,
            name = request.name!!,
            description = "",
            status = request.status!!,
            createdDtm = LocalDateTime.now(),
            modifiedDtm = LocalDateTime.now(),
            category = CategoryDto(
                id = 1L,
                name = request.category!!.name!!
            )
        )

        every { taskService.saveTask(any()) } returns savedTask

        val mvcResult = mockMvc.perform(
            post("/tasks")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isCreated)
            .andReturn()

        val actualTask: TaskDto = objectMapper.readValue(mvcResult.response.contentAsString, TaskDto::class.java)
        assertThat(actualTask.id).isEqualTo(savedTask.id)
        assertThat(actualTask.name).isEqualTo(savedTask.name)
        assertThat(actualTask.status).isEqualTo(savedTask.status)
        assertThat(actualTask.description).isEqualTo(savedTask.description)
        assertThat(actualTask.createdDtm).isEqualTo(savedTask.createdDtm)
        assertThat(actualTask.modifiedDtm).isEqualTo(savedTask.modifiedDtm)

        val actualCategory = actualTask.category!!
        assertThat(actualCategory).isNotNull
        assertThat(actualCategory.id).isEqualTo(savedTask.category!!.id)
        assertThat(actualCategory.name).isEqualTo(savedTask.category!!.name)
    }

    @DisplayName("Task 등록 - 실패 (유효성 검사 실패)")
    @CsvSource(
        ",PLANNING,Category",
        "Task,,Category",
        "Task,PLANNING,",
    )
    @ParameterizedTest
    fun saveTask_whenInvalidRequest(taskName: String?, taskStatus: TaskStatus?, categoryName: String?) {
        val request = TaskSaveRequest(
            name = taskName,
            status = taskStatus,
            category = CategorySaveRequest(
                name = categoryName
            )
        )

        val mvcResult = mockMvc.perform(
            post("/tasks")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isBadRequest)
            .andReturn()

        val actualResponse: ErrorResponse = objectMapper.readValue(mvcResult.response.contentAsString, ErrorResponse::class.java)
        assertThat(actualResponse.code).isEqualTo(ErrorType.INVALID_REQUEST.code)
    }

}
