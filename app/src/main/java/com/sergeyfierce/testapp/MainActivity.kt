package com.sergeyfierce.testapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import com.sergeyfierce.testapp.ui.theme.TouchNotebookTheme
import com.sergeyfierce.testapp.ui.TouchNotebookApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TouchNotebookTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    TouchNotebookApp()
                }
            }
        }
    }
}
