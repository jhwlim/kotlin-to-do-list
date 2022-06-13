package com.example.exception

import com.example.common.advice.BaseException
import com.example.common.enums.ErrorType
import org.springframework.http.HttpStatus

class TaskNotFoundException : BaseException(
    httpStatus = HttpStatus.NOT_FOUND,
    errorType = ErrorType.TASK_NOT_FOUND,
)
