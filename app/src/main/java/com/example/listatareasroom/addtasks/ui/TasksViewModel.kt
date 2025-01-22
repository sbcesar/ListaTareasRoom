package com.example.listatareasroom.addtasks.ui

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.listatareasroom.addtasks.ui.model.TaskModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor() : ViewModel() {

    // Los LiveData no van bien con los listados que se tienen que ir actualizando...
    // Para solucionarlo, podemos utilizar un mutableStateListOf porque se lleva mejor con LazyColumn a la hora de refrescar la información en la vista
    private val _tasks = mutableStateListOf<TaskModel>()
    val tasks: List<TaskModel> = _tasks

    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog = _showDialog

    private val _myTaskText = MutableLiveData<String>()
    val myTaskText = _myTaskText

    fun onDialogClose() {
        _showDialog.value = false
    }

    fun onTaskCreated() {
        onDialogClose()
        // Log.i("dam2", _myTaskText.value ?: "")
        _tasks.add(TaskModel(task = _myTaskText.value ?: ""))
        _myTaskText.value = ""
    }

    fun onItemRemove(taskModel: TaskModel) {
        val task = _tasks.find { it.id == taskModel.id }
        _tasks.remove(task)
    }

    fun onCheckBoxSelected(taskModel: TaskModel) {
        val index = _tasks.indexOf(taskModel)
        _tasks[index] = _tasks[index].let { it.copy(selected = !it.selected) }
    }

    fun onShowDialogClick() {
        _showDialog.value = true
    }

    fun onTaskTextChanged(taskTest: String) {
        _myTaskText.value = taskTest
    }

}