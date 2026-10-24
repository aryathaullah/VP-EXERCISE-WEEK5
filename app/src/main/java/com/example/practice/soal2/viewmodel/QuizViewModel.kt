package com.example.practice.soal2.viewmodel

import androidx.lifecycle.ViewModel
import com.example.practice.soal2.model.Question
import com.example.practice.soal2.model.QuizAnswer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class QuizViewModel : ViewModel() {

    private val allQuestions = listOf(
        Question("Kapan ulang tahun arya?", listOf("12 Januari", "25 Maret", "17 Agustus", "5 Desember"), "25 Maret"),
        Question("Berapa ukuran sepatu chris?", listOf("40", "41", "42", "43"), "42"),
        Question("Apa makanan favorit ruben?", listOf("Nasi Goreng", "Sate Ayam", "Bakso", "Mie Ayam"), "Mie Ayam"),
        Question("Berapa tinggi badan ben?", listOf("165 cm", "170 cm", "175 cm", "180 cm"), "175 cm"),
        Question("Apa warna favorit kevin?", listOf("Biru", "Hitam", "Merah", "Hijau"), "Hitam"),
        Question("Berapa jam john biasanya tidur?", listOf("4 jam", "6 jam", "8 jam", "10 jam"), "6 jam"),
        Question("Apa minuman favorit darren?", listOf("Kopi Susu", "Es Teh", "Jus Alpukat", "Air Mineral"), "Es Teh"),
        Question("Apa kebiasaan jesson saat mengerjakan tugas?", listOf("Tidur dulu", "Mendengarkan musik", "Menonton film", "Bermain bola"), "Mendengarkan musik")
    )

    val maxQuestions = allQuestions.size

    private val _questions = MutableStateFlow<List<Question>>(emptyList())
    val questions: StateFlow<List<Question>> = _questions.asStateFlow()

    private val _questionIndex = MutableStateFlow(0)
    val questionIndex: StateFlow<Int> = _questionIndex.asStateFlow()

    private val _selectedAnswer = MutableStateFlow("")
    val selectedAnswer: StateFlow<String> = _selectedAnswer.asStateFlow()

    private val _quizAnswers = MutableStateFlow<List<QuizAnswer>>(emptyList())
    val quizAnswers: StateFlow<List<QuizAnswer>> = _quizAnswers.asStateFlow()

    private val _isStarted = MutableStateFlow(false)
    val isStarted: StateFlow<Boolean> = _isStarted.asStateFlow()

    private val _isFinished = MutableStateFlow(false)
    val isFinished: StateFlow<Boolean> = _isFinished.asStateFlow()

    val score: Int
        get() = _quizAnswers.value.count { it.selectedAnswer == it.question.correctAnswer }

    val wrongAnswers: List<QuizAnswer>
        get() = _quizAnswers.value.filter { it.selectedAnswer != it.question.correctAnswer }

    fun startQuiz(number: Int) {
        if (number < 1 || number > maxQuestions) return
        _questions.value = allQuestions.shuffled().take(number)
        _questionIndex.value = 0
        _selectedAnswer.value = ""
        _quizAnswers.value = emptyList()
        _isFinished.value = false
        _isStarted.value = true
    }

    fun selectAnswer(answer: String) {
        _selectedAnswer.value = answer
    }

    fun nextQuestion() {
        if (_selectedAnswer.value.isEmpty() || _isFinished.value) return
        val index = _questionIndex.value
        if (index !in _questions.value.indices) return

        _quizAnswers.update { it + QuizAnswer(_questions.value[index], _selectedAnswer.value) }
        _selectedAnswer.value = ""

        if (index + 1 == _questions.value.size) {
            _isFinished.value = true
        } else {
            _questionIndex.value = index + 1
        }
    }

    fun restartQuiz() {
        startQuiz(_questions.value.size)
    }
}
