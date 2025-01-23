package com.example.listatareasroom.addtasks.data

import com.example.listatareasroom.addtasks.ui.model.TaskModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(private val taskdao: TaskDao) {
    val tasks: Flow<List<TaskModel>> = taskdao.getTask().map { items ->
        items.map {
            TaskModel(it.id, it.task, it.selected)
        }
    }
}