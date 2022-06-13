package com.example.common.enums

enum class ErrorType(
    val code: Int,
    val message: String,
) {

    /* Task */
    TASK_NOT_FOUND(1001, "Task를 찾을 수 없습니다."),

    /* 공통 */
    INVALID_REQUEST(9001, "잘못된 요청입니다."),
    UNKNOWN(9999, "알 수 없는 에러가 발생했습니다.")

}
