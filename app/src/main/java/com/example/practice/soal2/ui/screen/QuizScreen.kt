
package com.example.practice.soal2.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.practice.soal2.viewmodel.QuizViewModel

@Composable
fun QuizScreen(viewModel: QuizViewModel = viewModel()) {
    val isStarted by viewModel.isStarted.collectAsState()
    val isFinished by viewModel.isFinished.collectAsState()
    val questions by viewModel.questions.collectAsState()
    val questionIndex by viewModel.questionIndex.collectAsState()
    val selectedAnswer by viewModel.selectedAnswer.collectAsState()
    val answers by viewModel.quizAnswers.collectAsState()

    if (!isStarted) {
        StartQuizScreen(
            maxQuestions = viewModel.maxQuestions,
            onStart = viewModel::startQuiz
        )
    } else if (isFinished) {
        ResultScreen(
            score = viewModel.score,
            total = answers.size,
            wrongAnswers = viewModel.wrongAnswers,
            onRestart = viewModel::restartQuiz
        )
    } else if (questions.isNotEmpty()) {
        QuestionScreen(
            question = questions[questionIndex],
            number = questionIndex + 1,
            selectedAnswer = selectedAnswer,
            onSelect = viewModel::selectAnswer,
            onNext = viewModel::nextQuestion
        )
    }
}
