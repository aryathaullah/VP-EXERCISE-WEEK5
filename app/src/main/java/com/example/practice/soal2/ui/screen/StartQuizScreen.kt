package com.example.practice.soal2.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StartQuizScreen(maxQuestions: Int, onStart: (Int) -> Unit) {
    var amount by remember { mutableStateOf("3") }
    var showError by remember { mutableStateOf(false) }
    val number = amount.toIntOrNull()

    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFFFFF8FF)).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Mini Quiz", fontSize = 24.sp)
        Text("How many questions do you want? (1 - $maxQuestions)", fontSize = 13.sp)
        Spacer(Modifier.height(24.dp))
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it; showError = false },
            label = { Text("Number of questions") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = showError,
            modifier = Modifier.fillMaxWidth()
        )
        if (showError) {
            Text("Jumlah soal harus 1 sampai $maxQuestions", color = Color.Red, fontSize = 12.sp)
        }
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = {
                if (number != null && number in 1..maxQuestions) onStart(number)
                else showError = true
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF536596)),
            modifier = Modifier.fillMaxWidth()
        ) { Text("Start Quiz") }
    }
}
