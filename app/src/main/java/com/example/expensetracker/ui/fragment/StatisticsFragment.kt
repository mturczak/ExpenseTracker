package com.example.expensetracker.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expensetracker.R
import com.example.expensetracker.databinding.FragmentStatisticsBinding
import com.example.expensetracker.ui.adapter.CategoryStatAdapter
import com.example.expensetracker.ui.viewmodel.ExpenseViewModel
import com.example.expensetracker.utils.ExpenseCategory
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter

class StatisticsFragment : Fragment() {

    private var _binding: FragmentStatisticsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ExpenseViewModel by viewModels()
    private lateinit var categoryStatAdapter: CategoryStatAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupPieChart()
        setupObservers()
    }

    private fun setupRecyclerView() {
        categoryStatAdapter = CategoryStatAdapter()

        binding.recyclerViewCategories.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = categoryStatAdapter
        }
    }

    private fun setupPieChart() {
        binding.pieChart.apply {
            setUsePercentValues(true)
            description.isEnabled = false
            setExtraOffsets(5f, 10f, 5f, 5f)

            dragDecelerationFrictionCoef = 0.95f

            isDrawHoleEnabled = true
            setHoleColor(Color.WHITE)

            setTransparentCircleColor(Color.WHITE)
            setTransparentCircleAlpha(110)

            holeRadius = 58f
            transparentCircleRadius = 61f

            setDrawCenterText(true)
            centerText = "Kategorie"

            rotationAngle = 0f
            isRotationEnabled = true
            isHighlightPerTapEnabled = true

            animateY(1400, Easing.EaseInOutQuad)

            legend.isEnabled = false
        }
    }

    private fun setupObservers() {
        viewModel.totalAmount.observe(viewLifecycleOwner) { total ->
            binding.tvTotalAmount.text = String.format("%.2f zł", total ?: 0.0)
        }

        viewModel.allExpenses.observe(viewLifecycleOwner) { expenses ->
            if (expenses.isNotEmpty()) {
                val average = (viewModel.totalAmount.value ?: 0.0) / expenses.size
                binding.tvAverageAmount.text = String.format("%.2f zł", average)
            } else {
                binding.tvAverageAmount.text = "0.00 zł"
            }
        }

        viewModel.categoryTotals.observe(viewLifecycleOwner) { categoryTotals ->
            categoryStatAdapter.submitList(categoryTotals)
            updatePieChart(categoryTotals)
        }
    }

    private fun updatePieChart(categoryTotals: List<com.example.expensetracker.data.dao.CategoryTotal>) {
        if (categoryTotals.isEmpty()) {
            binding.pieChart.clear()
            return
        }

        val entries = ArrayList<PieEntry>()
        val colors = ArrayList<Int>()

        for (categoryTotal in categoryTotals) {
            entries.add(PieEntry(categoryTotal.total.toFloat(), categoryTotal.category))
            colors.add(ContextCompat.getColor(requireContext(), getCategoryColor(categoryTotal.category)))
        }

        val dataSet = PieDataSet(entries, "Wydatki").apply {
            setDrawIcons(false)
            sliceSpace = 3f
            selectionShift = 5f
            setColors(colors)
            valueTextColor = Color.WHITE
            valueTextSize = 12f
        }

        val data = PieData(dataSet).apply {
            setValueFormatter(PercentFormatter(binding.pieChart))
            setValueTextSize(11f)
            setValueTextColor(Color.WHITE)
        }

        binding.pieChart.data = data
        binding.pieChart.highlightValues(null)
        binding.pieChart.invalidate()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

