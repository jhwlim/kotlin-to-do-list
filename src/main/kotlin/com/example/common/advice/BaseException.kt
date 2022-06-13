package com.example.common.advice

import com.example.common.enums.ErrorType
import org.springframework.http.HttpStatus

open class BaseException(
    val httpStatus: HttpStatus,
    val errorType: ErrorType,
) : RuntimeException(errorType.message)
