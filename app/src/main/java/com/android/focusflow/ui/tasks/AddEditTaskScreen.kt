package com.android.focusflow.ui.tasks

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.focusflow.data.model.Task
import com.android.focusflow.ui.theme.*
import com.android.focusflow.viewmodel.TasksViewModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTaskScreen(
    taskId: Long? = null,
    viewModel: TasksViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var priority by remember { mutableIntStateOf(0) }
    var dueDate by remember { mutableStateOf<Long?>(null) }
    var reminderTime by remember { mutableStateOf<Long?>(null) }

    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    val allTasks by viewModel.allTasks.collectAsState()
    
    LaunchedEffect(taskId) {
        if (taskId != null) {
            allTasks.firstOrNull { it.id == taskId }?.let { task ->
                title = task.title
                description = task.description
                priority = task.priority
                dueDate = task.dueDate
                reminderTime = task.reminderTime
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (taskId == null) "New Task" else "Edit Task", color = PureWhite) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = PureWhite)
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            if (title.isNotBlank()) {
                                val task = if (taskId == null) {
                                    Task(
                                        title = title,
                                        description = description,
                                        priority = priority,
                                        dueDate = dueDate,
                                        reminderTime = reminderTime
                                    )
                                } else {
                                    Task(
                                        id = taskId,
                                        title = title,
                                        description = description,
                                        priority = priority,
                                        dueDate = dueDate,
                                        reminderTime = reminderTime
                                    )
                                }
                                if (taskId == null) {
                                    viewModel.insertTask(task)
                                } else {
                                    viewModel.updateTask(task)
                                }
                                onNavigateBack()
                            }
                        }
                    ) {
                        Icon(Icons.Default.Check, contentDescription = "Save", tint = ElectricBlue)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DarkGray
                )
            )
        },
        containerColor = DarkBlack
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title", color = LightGray) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = PureWhite,
                    unfocusedTextColor = PureWhite,
                    focusedContainerColor = DarkGray,
                    unfocusedContainerColor = DarkGray,
                    focusedIndicatorColor = ElectricBlue,
                    unfocusedIndicatorColor = MediumGray,
                    focusedLabelColor = ElectricBlue,
                    unfocusedLabelColor = LightGray
                ),
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description", color = LightGray) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = PureWhite,
                    unfocusedTextColor = PureWhite,
                    focusedContainerColor = DarkGray,
                    unfocusedContainerColor = DarkGray,
                    focusedIndicatorColor = ElectricBlue,
                    unfocusedIndicatorColor = MediumGray,
                    focusedLabelColor = ElectricBlue,
                    unfocusedLabelColor = LightGray
                ),
                shape = RoundedCornerShape(12.dp),
                minLines = 3,
                maxLines = 5
            )

            // Priority selector
            Text(
                text = "Priority",
                style = MaterialTheme.typography.titleSmall,
                color = PureWhite
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("Low", "Medium", "High").forEachIndexed { index, label ->
                    FilterChip(
                        selected = priority == index,
                        onClick = { priority = index },
                        label = { Text(label, color = if (priority == index) PureWhite else LightGray) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = ElectricBlue,
                            containerColor = DarkGray
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Due date
            if (showDatePicker) {
                val datePickerState = rememberDatePickerState(
                    initialSelectedDateMillis = dueDate
                )
                DatePickerDialog(
                    onDismissRequest = { showDatePicker = false },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                datePickerState.selectedDateMillis?.let {
                                    dueDate = it
                                }
                                showDatePicker = false
                            }
                        ) {
                            Text("OK", color = ElectricBlue)
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { showDatePicker = false }
                        ) {
                            Text("Cancel", color = LightGray)
                        }
                    }
                ) {
                    DatePicker(
                        state = datePickerState
                    )
                }
            }

            OutlinedButton(
                onClick = { showDatePicker = true },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = ElectricBlue
                )
            ) {
                Text(
                    text = if (dueDate != null) {
                        val dateFormat = java.text.SimpleDateFormat("MMM dd, yyyy", java.util.Locale.getDefault())
                        "Due Date: ${dateFormat.format(java.util.Date(dueDate!!))}"
                    } else {
                        "Set Due Date"
                    },
                    color = if (dueDate != null) ElectricBlue else LightGray
                )
            }

            if (dueDate != null) {
                TextButton(
                    onClick = { dueDate = null },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Remove Due Date")
                }
            }
        }
    }
}

