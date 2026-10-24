package com.example.practice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import com.example.practice.soal1.ui.screen.ExpenseScreen
import com.example.practice.soal2.ui.screen.QuizScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                // ExpenseScreen() // soal 1
                QuizScreen() // soal 2
            }
        }
    }
}
