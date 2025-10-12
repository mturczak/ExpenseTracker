package com.example.expensetracker.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.data.dao.CategoryTotal
import com.example.expensetracker.data.database.ExpenseDatabase
import com.example.expensetracker.data.model.ExpenseEntity
import com.example.expensetracker.data.repository.ExpenseRepository
import kotlinx.coroutines.launch

class ExpenseViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ExpenseRepository
    val allExpenses: LiveData<List<ExpenseEntity>>
    val totalAmount: LiveData<Double?>
    val categoryTotals: LiveData<List<CategoryTotal>>

    init {
        val expenseDao = ExpenseDatabase.getDatabase(application).expenseDao()
        repository = ExpenseRepository(expenseDao)
        allExpenses = repository.allExpenses
        totalAmount = repository.totalAmount
        categoryTotals = repository.categoryTotals
    }

    fun insert(expense: ExpenseEntity) = viewModelScope.launch {
        repository.insert(expense)
    }

    fun update(expense: ExpenseEntity) = viewModelScope.launch {
        repository.update(expense)
    }

    fun delete(expense: ExpenseEntity) = viewModelScope.launch {
        repository.delete(expense)
    }

    fun getExpensesByDateRange(startDate: Long, endDate: Long): LiveData<List<ExpenseEntity>> {
        return repository.getExpensesByDateRange(startDate, endDate)
    }

    fun getExpensesByCategory(category: String): LiveData<List<ExpenseEntity>> {
        return repository.getExpensesByCategory(category)
    }

    fun getTotalAmountByDateRange(startDate: Long, endDate: Long): LiveData<Double?> {
        return repository.getTotalAmountByDateRange(startDate, endDate)
    }

    fun getCategoryTotalsByDateRange(startDate: Long, endDate: Long): LiveData<List<CategoryTotal>> {
        return repository.getCategoryTotalsByDateRange(startDate, endDate)
    }
}

