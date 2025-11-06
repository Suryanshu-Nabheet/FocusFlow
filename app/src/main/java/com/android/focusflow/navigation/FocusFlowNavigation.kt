package com.android.focusflow.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.android.focusflow.ui.calendar.CalendarScreen
import com.android.focusflow.ui.notes.AddEditNoteScreen
import com.android.focusflow.ui.notes.NotesScreen
import com.android.focusflow.ui.tasks.AddEditTaskScreen
import com.android.focusflow.ui.tasks.TasksScreen
import com.android.focusflow.ui.timer.TimerScreen

sealed class Screen(val route: String) {
    object Notes : Screen("notes")
    object Tasks : Screen("tasks")
    object Calendar : Screen("calendar")
    object Timer : Screen("timer")
    object AddNote : Screen("add_note")
    object EditNote : Screen("edit_note/{noteId}") {
        fun createRoute(noteId: Long) = "edit_note/$noteId"
    }
    object AddTask : Screen("add_task")
    object EditTask : Screen("edit_task/{taskId}") {
        fun createRoute(taskId: Long) = "edit_task/$taskId"
    }
}

@Composable
fun FocusFlowNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = Screen.Notes.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier.fillMaxSize()
    ) {
        composable(Screen.Notes.route) {
            NotesScreen(
                onNoteClick = { note ->
                    navController.navigate(Screen.EditNote.createRoute(note.id))
                },
                onAddNote = {
                    navController.navigate(Screen.AddNote.route)
                }
            )
        }

        composable(Screen.AddNote.route) {
            AddEditNoteScreen(
                noteId = null,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.EditNote.route) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId")?.toLongOrNull()
            AddEditNoteScreen(
                noteId = noteId,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Tasks.route) {
            TasksScreen(
                onTaskClick = { task ->
                    navController.navigate(Screen.EditTask.createRoute(task.id))
                },
                onAddTask = {
                    navController.navigate(Screen.AddTask.route)
                }
            )
        }

        composable(Screen.AddTask.route) {
            AddEditTaskScreen(
                taskId = null,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.EditTask.route) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId")?.toLongOrNull()
            AddEditTaskScreen(
                taskId = taskId,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Calendar.route) {
            CalendarScreen()
        }

        composable(Screen.Timer.route) {
            TimerScreen()
        }
    }
}

