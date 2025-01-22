package com.example.listatareasroom.addtasks.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.listatareasroom.addtasks.ui.model.TaskModel

@Composable
fun TasksScreen(tasksViewModel: TasksViewModel) {

    val showDialog by tasksViewModel.showDialog.observeAsState(false)
    val myTaskText by tasksViewModel.myTaskText.observeAsState("")

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AddTaskDialog(
            show = showDialog,
            myTaskText = myTaskText,
            onDismiss = { tasksViewModel.onDialogClose() },
            onTaskAdded = { tasksViewModel.onTaskCreated() },
            onTaskTextChanged = { tasksViewModel.onTaskTextChanged(it) }
        )
        FabDialog(Modifier.align(Alignment.BottomEnd), onNewTask = { tasksViewModel.onShowDialogClick() })
        TaskList(tasksViewModel)
    }
}

@Composable
fun TaskList(tasksViewModel: TasksViewModel) {
    val myTasks: List<TaskModel> = tasksViewModel.tasks

    LazyColumn {
        items(myTasks, key = { it.id }) { task ->
            ItemTask(
                task,
                onTaskRemove = { tasksViewModel.onItemRemove(it) },
                onTaskCheckChanged = { tasksViewModel.onCheckBoxSelected(it) }
            )
        }
    }
}

@Composable
fun ItemTask (
    taskModel: TaskModel,
    onTaskRemove: (TaskModel) -> Unit,
    onTaskCheckChanged: (TaskModel) -> Unit
) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .pointerInput(Unit) {
                detectTapGestures(onLongPress = {
                    onTaskRemove(taskModel)
                })
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = taskModel.task,
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .weight(1f)
            )
            Checkbox(
                checked = taskModel.selected,
                onCheckedChange = { onTaskCheckChanged(taskModel) }
            )
        }
    }
}

@Composable
fun AddTaskDialog(
    show: Boolean,
    myTaskText: String,
    onDismiss: () -> Unit,
    onTaskAdded: () -> Unit,
    onTaskTextChanged: (String) -> Unit
) {
    if (show) {
        Dialog(
            onDismissRequest = { onDismiss() }
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Añade tu tarea",
                    fontSize = 18.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.size(16.dp))
                TextField(
                    value = myTaskText,
                    onValueChange = { onTaskTextChanged(it) },
                    singleLine = true,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.size(16.dp))
                Button(
                    onClick = { onTaskAdded() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Añadir tarea")
                }
            }
        }
    }

}

@Composable
fun FabDialog(modifier: Modifier, onNewTask: () -> Unit) {
    FloatingActionButton(
        onClick = { onNewTask() },
        modifier = modifier.padding(16.dp)
    ) {
        Icon(
            Icons.Filled.Add,
            contentDescription = "Floating Action Icon"
        )
    }
}