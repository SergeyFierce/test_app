package com.sergeyfierce.testapp.ui.notes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteAddScreen(onBack: () -> Unit, onDone: () -> Unit) {
    var text by remember { mutableStateOf("") }
    var date by remember { mutableStateOf(System.currentTimeMillis()) }
    var showDatePicker by remember { mutableStateOf(false) }

    if (showDatePicker) {
        val state = rememberDatePickerState(initialSelectedDateMillis = date, selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean = utcTimeMillis <= System.currentTimeMillis()
        })
        DatePickerDialog(onDismissRequest = { showDatePicker = false }, confirmButton = {
            TextButton(onClick = {
                state.selectedDateMillis?.let { date = it }
                showDatePicker = false
            }) { Text("OK") }
        }) { DatePicker(state = state) }
    }

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text("Добавить заметку") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null) } },
                actions = { TextButton(onClick = onDone) { Text("Сохранить") } }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedTextField(value = text, onValueChange = { text = it }, label = { Text("Текст заметки") }, modifier = Modifier.fillMaxWidth(), singleLine = false)
            OutlinedTextField(
                value = java.text.SimpleDateFormat("dd.MM.yyyy").format(Date(date)),
                onValueChange = {},
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showDatePicker = true },
                label = { Text("Дата добавления") }
            )
        }
    }
}
