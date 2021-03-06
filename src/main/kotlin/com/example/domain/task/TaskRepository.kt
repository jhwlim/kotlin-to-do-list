package com.example.domain.task

import org.springframework.data.jpa.repository.JpaRepository

interface TaskRepository : JpaRepository<Task, Long> {

    fun <T> findAllByOrderByCreatedDtmDesc(type: Class<T>): List<T>

}
