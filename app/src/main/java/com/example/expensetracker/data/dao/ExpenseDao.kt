package com.example.expensetracker.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.expensetracker.data.model.ExpenseEntity

@Dao
interface ExpenseDao {

    @Insert
    suspend fun insert(expense: ExpenseEntity)

    @Update
    suspend fun update(expense: ExpenseEntity)

    @Delete
    suspend fun delete(expense: ExpenseEntity)

    @Query("SELECT * FROM expenses ORDER BY date DESC")
    fun getAllExpenses(): LiveData<List<ExpenseEntity>>

    @Query("SELECT * FROM expenses WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getExpensesByDateRange(startDate: Long, endDate: Long): LiveData<List<ExpenseEntity>>

    @Query("SELECT * FROM expenses WHERE category = :category ORDER BY date DESC")
    fun getExpensesByCategory(category: String): LiveData<List<ExpenseEntity>>

    @Query("SELECT SUM(amount) FROM expenses")
    fun getTotalAmount(): LiveData<Double?>

    @Query("SELECT SUM(amount) FROM expenses WHERE date BETWEEN :startDate AND :endDate")
    fun getTotalAmountByDateRange(startDate: Long, endDate: Long): LiveData<Double?>

    @Query("SELECT category, SUM(amount) as total FROM expenses GROUP BY category")
    fun getCategoryTotals(): LiveData<List<CategoryTotal>>

    @Query("SELECT category, SUM(amount) as total FROM expenses WHERE date BETWEEN :startDate AND :endDate GROUP BY category")
    fun getCategoryTotalsByDateRange(startDate: Long, endDate: Long): LiveData<List<CategoryTotal>>
}

data class CategoryTotal(
    val category: String,
    val total: Double
)

