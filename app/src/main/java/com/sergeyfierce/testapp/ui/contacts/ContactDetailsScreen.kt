package com.sergeyfierce.testapp.ui.contacts

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ContactDetailsScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text("Детали контакта") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null) } }
            )
        }
    ) { padding ->
        Text("Детали контакта", modifier = Modifier.padding(padding).padding(16.dp))
    }
}
