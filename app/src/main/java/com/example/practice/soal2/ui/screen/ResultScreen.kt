package com.example.practice.soal2.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.practice.soal2.model.QuizAnswer

@Composable
fun ResultScreen(score: Int, total: Int, wrongAnswers: List<QuizAnswer>, onRestart: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFFFFF8FF)).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Quiz Finished!", fontSize = 24.sp)
        Text("Your Score: $score / $total", fontSize = 16.sp)
        Spacer(Modifier.height(24.dp))

        if (wrongAnswers.isEmpty()) {
            Text("Perfect! No wrong answers.", color = Color(0xFF536596))
        } else {
            for (answer in wrongAnswers) {
                Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                    Text(answer.question.text, fontSize = 14.sp)
                    Text("Your Answer: ${answer.selectedAnswer}", color = Color.Red, fontSize = 12.sp)
                    Text("Correct Answer: ${answer.question.correctAnswer}", fontSize = 12.sp)
                }
                HorizontalDivider()
            }
        }

        Spacer(Modifier.height(18.dp))
        Button(
            onClick = onRestart,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF536596))
        ) { Text("Restart Quiz") }
    }
}
