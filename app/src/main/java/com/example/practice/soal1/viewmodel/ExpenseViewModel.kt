package com.example.practice.soal1.viewmodel

import androidx.lifecycle.ViewModel
import com.example.practice.soal1.model.Expense
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ExpenseViewModel : ViewModel() {

    private val expenseData = listOf(
        Expense("Nasi Pecel", 18000),
        Expense("Kopi", 50000)
    )
    private val _expenseList = MutableStateFlow<List<Expense>>(expenseData)
    val expenseList: StateFlow<List<Expense>> = _expenseList.asStateFlow()

    val totalExpense: Int
        get() = _expenseList.value.sumOf { it.amount }

    fun addExpense(name: String, amount: Int) {
        if (name.isBlank() || amount <= 0) return
        _expenseList.update { it + Expense(name.trim(), amount) }
    }

    fun deleteExpense(index: Int) {
        _expenseList.update { list ->
            if (index in list.indices) list.toMutableList().apply { removeAt(index) }
            else list
        }
    }

    fun editExpense(index: Int, amount: Int) {
        if (amount <= 0) return
        _expenseList.update { list ->
            if (index in list.indices) list.toMutableList().apply {
                this[index] = this[index].copy(amount = amount)
            } else list
        }
    }
}
