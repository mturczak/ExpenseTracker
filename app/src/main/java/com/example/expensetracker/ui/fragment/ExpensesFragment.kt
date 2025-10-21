package com.example.expensetracker.ui.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker.R
import com.example.expensetracker.data.model.ExpenseEntity
import com.example.expensetracker.databinding.DialogAddExpenseBinding
import com.example.expensetracker.databinding.FragmentExpensesBinding
import com.example.expensetracker.ui.adapter.ExpenseAdapter
import com.example.expensetracker.ui.viewmodel.ExpenseViewModel
import com.example.expensetracker.ui.helper.SwipeToDeleteCallback
import com.example.expensetracker.utils.ExpenseCategory
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

class ExpensesFragment : Fragment() {

    private var _binding: FragmentExpensesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ExpenseViewModel by viewModels()
    private lateinit var expenseAdapter: ExpenseAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExpensesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()
        setupClickListeners()
    }

    private fun setupRecyclerView() {
        expenseAdapter = ExpenseAdapter(
            onItemClick = { expense -> showEditExpenseDialog(expense) },
            onItemLongClick = { expense -> showDeleteConfirmationDialog(expense) }
        )

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = expenseAdapter
        }

        // Setup swipe to delete
        val swipeToDeleteCallback = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                val expense = expenseAdapter.currentList[position]

                // Delete expense
                viewModel.delete(expense)

                // Show snackbar with undo option
                Snackbar.make(binding.root, "Wydatek usunięty", Snackbar.LENGTH_LONG)
                    .setAction("COFNIJ") {
                        viewModel.insert(expense)
                    }
                    .setActionTextColor(resources.getColor(R.color.accent, null))
                    .show()
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    private fun setupObservers() {
        viewModel.allExpenses.observe(viewLifecycleOwner) { expenses ->
            expenseAdapter.submitList(expenses)

            if (expenses.isEmpty()) {
                binding.tvNoExpenses.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
            } else {
                binding.tvNoExpenses.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
            }
        }

        viewModel.totalAmount.observe(viewLifecycleOwner) { total ->
            binding.tvTotalAmount.text = String.format(Locale.getDefault(), "%.2f zł", total ?: 0.0)
        }
    }

    private fun setupClickListeners() {
        binding.fabAddExpense.setOnClickListener {
            showAddExpenseDialog()
        }
    }

    private fun showAddExpenseDialog() {
        val dialogBinding = DialogAddExpenseBinding.inflate(layoutInflater)
        var selectedDate = System.currentTimeMillis()

        // Setup category dropdown
        val categories = ExpenseCategory.getAllCategories()
        val categoryAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, categories)
        dialogBinding.actvCategory.setAdapter(categoryAdapter)

        // Setup date picker
        val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.forLanguageTag("pl-PL"))
        dialogBinding.btnSelectDate.text = dateFormat.format(Date(selectedDate))

        dialogBinding.btnSelectDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = selectedDate

            DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    calendar.set(year, month, dayOfMonth)
                    selectedDate = calendar.timeInMillis
                    dialogBinding.btnSelectDate.text = dateFormat.format(Date(selectedDate))
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.add_new_expense)
            .setView(dialogBinding.root)
            .create()

        dialogBinding.btnSave.setOnClickListener {
            if (validateInput(dialogBinding)) {
                val expense = ExpenseEntity(
                    amount = dialogBinding.etAmount.text.toString().toDouble(),
                    category = dialogBinding.actvCategory.text.toString(),
                    description = dialogBinding.etDescription.text.toString(),
                    date = selectedDate
                )
                viewModel.insert(expense)
                dialog.dismiss()
            }
        }

        dialogBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showEditExpenseDialog(expense: ExpenseEntity) {
        val dialogBinding = DialogAddExpenseBinding.inflate(layoutInflater)
        var selectedDate = expense.date

        // Pre-fill data
        dialogBinding.etAmount.setText(expense.amount.toString())
        dialogBinding.etDescription.setText(expense.description)

        // Setup category dropdown
        val categories = ExpenseCategory.getAllCategories()
        val categoryAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, categories)
        dialogBinding.actvCategory.setAdapter(categoryAdapter)
        dialogBinding.actvCategory.setText(expense.category, false)

        // Setup date picker
        val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.forLanguageTag("pl-PL"))
        dialogBinding.btnSelectDate.text = dateFormat.format(Date(selectedDate))

        dialogBinding.btnSelectDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = selectedDate

            DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    calendar.set(year, month, dayOfMonth)
                    selectedDate = calendar.timeInMillis
                    dialogBinding.btnSelectDate.text = dateFormat.format(Date(selectedDate))
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.edit_expense)
            .setView(dialogBinding.root)
            .create()

        dialogBinding.btnSave.setOnClickListener {
            if (validateInput(dialogBinding)) {
                val updatedExpense = expense.copy(
                    amount = dialogBinding.etAmount.text.toString().toDouble(),
                    category = dialogBinding.actvCategory.text.toString(),
                    description = dialogBinding.etDescription.text.toString(),
                    date = selectedDate
                )
                viewModel.update(updatedExpense)
                dialog.dismiss()
            }
        }

        dialogBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showDeleteConfirmationDialog(expense: ExpenseEntity) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.delete)
            .setMessage(R.string.delete_confirmation)
            .setPositiveButton(R.string.yes) { _, _ ->
                viewModel.delete(expense)
            }
            .setNegativeButton(R.string.no, null)
            .show()
    }

    private fun validateInput(binding: DialogAddExpenseBinding): Boolean {
        var isValid = true

        // Validate amount
        val amountText = binding.etAmount.text?.toString()
        if (amountText.isNullOrEmpty()) {
            binding.tilAmount.error = getString(R.string.error_empty_amount)
            isValid = false
        } else {
            try {
                val amount = amountText.toDouble()
                if (amount <= 0) {
                    binding.tilAmount.error = getString(R.string.error_invalid_amount)
                    isValid = false
                } else {
                    binding.tilAmount.error = null
                }
            } catch (e: NumberFormatException) {
                binding.tilAmount.error = getString(R.string.error_invalid_amount)
                isValid = false
            }
        }

        // Validate category
        if (binding.actvCategory.text.isNullOrEmpty()) {
            binding.tilCategory.error = getString(R.string.error_empty_category)
            isValid = false
        } else {
            binding.tilCategory.error = null
        }

        // Validate description
        if (binding.etDescription.text.isNullOrEmpty()) {
            binding.tilDescription.error = getString(R.string.error_empty_description)
            isValid = false
        } else {
            binding.tilDescription.error = null
        }

        return isValid
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
