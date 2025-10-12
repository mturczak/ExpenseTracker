package com.example.expensetracker.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker.R
import com.example.expensetracker.data.model.ExpenseEntity
import com.example.expensetracker.databinding.ItemExpenseBinding
import com.example.expensetracker.utils.ExpenseCategory
import java.text.SimpleDateFormat
import java.util.*

class ExpenseAdapter(
    private val onItemClick: (ExpenseEntity) -> Unit,
    private val onItemLongClick: (ExpenseEntity) -> Unit
) : ListAdapter<ExpenseEntity, ExpenseAdapter.ExpenseViewHolder>(ExpenseDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val binding = ItemExpenseBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ExpenseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ExpenseViewHolder(private val binding: ItemExpenseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(expense: ExpenseEntity) {
            binding.apply {
                tvCategory.text = expense.category
                tvDescription.text = expense.description
                tvAmount.text = String.format("%.2f zł", expense.amount)
                tvDate.text = formatDate(expense.date)

                // Set category color
                val categoryColor = getCategoryColor(expense.category)
                categoryIndicator.setBackgroundColor(
                    ContextCompat.getColor(binding.root.context, categoryColor)
                )

                root.setOnClickListener { onItemClick(expense) }
                root.setOnLongClickListener {
                    onItemLongClick(expense)
                    true
                }
            }
        }

        private fun formatDate(timestamp: Long): String {
            val sdf = SimpleDateFormat("dd MMM yyyy", Locale.forLanguageTag("pl-PL"))
            return sdf.format(Date(timestamp))
        }

        private fun getCategoryColor(category: String): Int {
            return when (category) {
                ExpenseCategory.FOOD.displayName -> R.color.category_food
                ExpenseCategory.TRANSPORT.displayName -> R.color.category_transport
                ExpenseCategory.ENTERTAINMENT.displayName -> R.color.category_entertainment
                ExpenseCategory.SHOPPING.displayName -> R.color.category_shopping
                ExpenseCategory.BILLS.displayName -> R.color.category_bills
                ExpenseCategory.HEALTH.displayName -> R.color.category_health
                ExpenseCategory.EDUCATION.displayName -> R.color.category_education
                else -> R.color.category_other
            }
        }
    }

    class ExpenseDiffCallback : DiffUtil.ItemCallback<ExpenseEntity>() {
        override fun areItemsTheSame(oldItem: ExpenseEntity, newItem: ExpenseEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ExpenseEntity, newItem: ExpenseEntity): Boolean {
            return oldItem == newItem
        }
    }
}
