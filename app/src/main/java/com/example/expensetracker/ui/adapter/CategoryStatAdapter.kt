package com.example.expensetracker.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker.R
import com.example.expensetracker.data.dao.CategoryTotal
import com.example.expensetracker.databinding.ItemCategoryStatBinding
import com.example.expensetracker.utils.ExpenseCategory

class CategoryStatAdapter : ListAdapter<CategoryTotal, CategoryStatAdapter.CategoryStatViewHolder>(CategoryStatDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryStatViewHolder {
        val binding = ItemCategoryStatBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CategoryStatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryStatViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CategoryStatViewHolder(private val binding: ItemCategoryStatBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(categoryTotal: CategoryTotal) {
            binding.apply {
                tvCategoryName.text = categoryTotal.category
                tvCategoryAmount.text = String.format("%.2f zł", categoryTotal.total)

                // Set category color
                val categoryColor = getCategoryColor(categoryTotal.category)
                categoryColorIndicator.setBackgroundColor(
                    ContextCompat.getColor(binding.root.context, categoryColor)
                )
            }
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

    class CategoryStatDiffCallback : DiffUtil.ItemCallback<CategoryTotal>() {
        override fun areItemsTheSame(oldItem: CategoryTotal, newItem: CategoryTotal): Boolean {
            return oldItem.category == newItem.category
        }

        override fun areContentsTheSame(oldItem: CategoryTotal, newItem: CategoryTotal): Boolean {
            return oldItem == newItem
        }
    }
}

