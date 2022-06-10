package com.example.domain.task

import com.example.common.enums.TaskStatus
import com.example.domain.category.Category
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "tasks")
open class Task(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long? = null,
    open var name: String,
    @Lob
    open var description: String = "",
    @Enumerated(EnumType.STRING)
    open var status: TaskStatus,
    open var createdDtm: LocalDateTime,
    open var modifiedDtm: LocalDateTime? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    open var category: Category? = null,
) {

}
