package com.example.common.advice

import com.example.common.enums.ErrorType
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    companion object {

        val log = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleInvalidRequestException(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        log.warn("[Invalid Request] : {}", e.fieldErrors)
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse.from(ErrorType.INVALID_REQUEST))
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ErrorResponse> {
        log.warn("", e)
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorResponse.from(ErrorType.UNKNOWN))
    }

}
