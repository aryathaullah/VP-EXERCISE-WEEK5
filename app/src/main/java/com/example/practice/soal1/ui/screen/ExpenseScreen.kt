package com.example.practice.soal1.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.practice.soal1.viewmodel.ExpenseViewModel

@Composable
fun ExpenseScreen(viewModel: ExpenseViewModel = viewModel()) {
    val expenseList by viewModel.expenseList.collectAsState()
    var name by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    var editIndex by remember { mutableStateOf(-1) }
    var editAmount by remember { mutableStateOf("") }
    var editError by remember { mutableStateOf(false) }
    val amountNumber = amount.toIntOrNull()
    val amountInvalid = showError && (amountNumber == null || amountNumber <= 0)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF8FF))
            .padding(start = 16.dp, top = 80.dp, end = 16.dp, bottom = 16.dp)
    ) {
        Text("Expenses", fontSize = 25.sp, fontWeight = FontWeight.ExtraBold)
        Text("Total Expense : ${rupiah(viewModel.totalExpense)}", fontSize = 14.sp)
        Spacer(Modifier.size(12.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Expense Name") },
            singleLine = true,
            isError = showError && name.isBlank(),
            modifier = Modifier.fillMaxWidth()
        )
        if (showError && name.isBlank()) {
            Text("Expense Name tidak boleh kosong", color = Color.Red, fontSize = 12.sp)
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Amount of Expense") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                isError = amountInvalid,
                modifier = Modifier.weight(1f)
            )
            IconButton(
                onClick = {
                    showError = true
                    val number = amount.toIntOrNull()
                    if (name.isNotBlank() && number != null && number > 0) {
                        viewModel.addExpense(name, number)
                        name = ""
                        amount = ""
                        showError = false
                    }
                },
                modifier = Modifier
                    .padding(top = 12.dp, start = 8.dp)
                    .background(Color(0xFFE5E5EB), CircleShape)
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = "Tambah expense",
                    tint = Color.Gray
                )
            }
        }
        if (amountInvalid) {
            Text("Amount must be greater than 0", color = Color.Red, fontSize = 12.sp)
        }

        Spacer(Modifier.size(12.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            itemsIndexed(expenseList) { index, expense ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFDFE4F6), RoundedCornerShape(8.dp))
                        .padding(start = 12.dp, top = 6.dp, bottom = 6.dp, end = 4.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Name : ${expense.name}", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text(rupiah(expense.amount), fontSize = 12.sp)
                    }
                    IconButton(onClick = {
                        editIndex = index
                        editAmount = expense.amount.toString()
                        editError = false
                    }) {
                        Icon(Icons.Filled.Edit, "Edit", tint = Color(0xFFB99000))
                    }
                    IconButton(onClick = { viewModel.deleteExpense(index) }) {
                        Icon(Icons.Filled.Delete, "Delete", tint = Color.Red)
                    }
                }
            }
        }
    }

    if (editIndex >= 0) {
        AlertDialog(
            onDismissRequest = { editIndex = -1 },
            title = { Text("Edit Amount") },
            text = {
                Column {
                    OutlinedTextField(
                        value = editAmount,
                        onValueChange = { editAmount = it },
                        label = { Text("Amount of Expense") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        isError = editError
                    )
                    if (editError) Text("Amount must be greater than 0", color = Color.Red)
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    val number = editAmount.toIntOrNull()
                    if (number != null && number > 0) {
                        viewModel.editExpense(editIndex, number)
                        editIndex = -1
                    } else {
                        editError = true
                    }
                }) { Text("Save") }
            },
            dismissButton = {
                TextButton(onClick = { editIndex = -1 }) { Text("Cancel") }
            }
        )
    }
}

fun rupiah(amount: Int): String {
    return "Rp ${String.format("%,d", amount).replace(",", ".")}"
}
