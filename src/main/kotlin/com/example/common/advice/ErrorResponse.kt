package com.example.common.advice

import com.example.common.enums.ErrorType

data class ErrorResponse(
    val code: Int,
    val message: String,
) {

    companion object {

        fun from(errorType: ErrorType): ErrorResponse {
            return ErrorResponse(errorType.code, errorType.message)
        }

    }

}
