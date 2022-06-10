package com.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinToDoListApplication

fun main(args: Array<String>) {
	runApplication<KotlinToDoListApplication>(*args)
}
