package com.example.expensetracker.data.repository

import androidx.lifecycle.LiveData
import com.example.expensetracker.data.dao.CategoryTotal
import com.example.expensetracker.data.dao.ExpenseDao
import com.example.expensetracker.data.model.ExpenseEntity

class ExpenseRepository(private val expenseDao: ExpenseDao) {

    val allExpenses: LiveData<List<ExpenseEntity>> = expenseDao.getAllExpenses()
    val totalAmount: LiveData<Double?> = expenseDao.getTotalAmount()
    val categoryTotals: LiveData<List<CategoryTotal>> = expenseDao.getCategoryTotals()

    suspend fun insert(expense: ExpenseEntity) {
        expenseDao.insert(expense)
    }

    suspend fun update(expense: ExpenseEntity) {
        expenseDao.update(expense)
    }

    suspend fun delete(expense: ExpenseEntity) {
        expenseDao.delete(expense)
    }

    fun getExpensesByDateRange(startDate: Long, endDate: Long): LiveData<List<ExpenseEntity>> {
        return expenseDao.getExpensesByDateRange(startDate, endDate)
    }

    fun getExpensesByCategory(category: String): LiveData<List<ExpenseEntity>> {
        return expenseDao.getExpensesByCategory(category)
    }

    fun getTotalAmountByDateRange(startDate: Long, endDate: Long): LiveData<Double?> {
        return expenseDao.getTotalAmountByDateRange(startDate, endDate)
    }

    fun getCategoryTotalsByDateRange(startDate: Long, endDate: Long): LiveData<List<CategoryTotal>> {
        return expenseDao.getCategoryTotalsByDateRange(startDate, endDate)
    }
}

