package com.example.domain.category


import com.example.domain.task.Task
import javax.persistence.*

@Entity
@Table(name = "categories")
open class Category (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long? = null,
    open var name: String,

    @OneToMany(mappedBy = "category")
    open var tasks: MutableSet<Task>? = null
) {

    fun addTasks(task: Task) {
        this.tasks?.add(task)
        task.category = this
    }

}
