package com.sergeyfierce.testapp.ui.contacts

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sergeyfierce.testapp.model.Category
import com.sergeyfierce.testapp.model.Status
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactAddScreen(
    preselectedCategory: String?,
    onDone: () -> Unit,
    onBack: () -> Unit
) {
    var fio by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var category by remember { mutableStateOf(preselectedCategory?.let { Category.valueOf(it) }) }
    var status by remember { mutableStateOf<Status?>(null) }
    var showDatePicker by remember { mutableStateOf(false) }
    var birthDate by remember { mutableStateOf<Long?>(null) }

    val statusOptions = when (category) {
        Category.PARTNER, Category.CLIENT -> listOf(Status.ACTIVE, Status.PASSIVE, Status.LOST)
        Category.POTENTIAL -> listOf(Status.COLD, Status.WARM, Status.LOST)
        null -> emptyList()
    }

    if (showDatePicker) {
        val state = rememberDatePickerState(selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean = utcTimeMillis <= System.currentTimeMillis()
        })
        DatePickerDialog(onDismissRequest = { showDatePicker = false }, confirmButton = {
            TextButton(onClick = {
                birthDate = state.selectedDateMillis
                showDatePicker = false
            }) { Text("OK") }
        }) {
            DatePicker(state = state)
        }
    }

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text("Добавить контакт") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null) } },
                actions = {
                    TextButton(onClick = onDone) { Text("Сохранить") }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedTextField(value = fio, onValueChange = { fio = it }, label = { Text("ФИО") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("Телефон") }, modifier = Modifier.fillMaxWidth())
            ExposedDropdownMenuBox(expanded = false, onExpandedChange = {}) {
                OutlinedTextField(
                    value = category?.title ?: "",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Категория") },
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = false) },
                    placeholder = { Text("Выберите категорию") }
                )
                DropdownMenu(expanded = false, onDismissRequest = {}) {}
            }
            ExposedDropdownMenuBox(expanded = false, onExpandedChange = {}) {
                OutlinedTextField(
                    value = status?.title ?: "",
                    onValueChange = {},
                    readOnly = true,
                    enabled = category != null,
                    label = { Text("Статус") },
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = false) },
                    placeholder = { Text(if (category == null) "Недоступно" else "Выберите статус") }
                )
                DropdownMenu(expanded = false, onDismissRequest = {}) {}
            }
            OutlinedTextField(
                value = birthDate?.let { Date(it).toString() } ?: "",
                onValueChange = {},
                readOnly = true,
                label = { Text("Дата рождения / Возраст") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showDatePicker = true }
            )
        }
    }
}
