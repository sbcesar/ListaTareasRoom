package com.example.listatareasroom.addtasks.ui.model

data class TaskModel(
    //El método hashcode() nos crea un código único a través del número de milisegundos
    val id: Int = System.currentTimeMillis().hashCode(),
    val task: String,
    var selected: Boolean = false
)
