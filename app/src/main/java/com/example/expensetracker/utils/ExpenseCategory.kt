package com.example.expensetracker.utils

enum class ExpenseCategory(val displayName: String) {
    FOOD("Jedzenie"),
    TRANSPORT("Transport"),
    ENTERTAINMENT("Rozrywka"),
    SHOPPING("Zakupy"),
    BILLS("Rachunki"),
    HEALTH("Zdrowie"),
    EDUCATION("Edukacja"),
    OTHER("Inne");

    companion object {
        fun fromString(value: String): ExpenseCategory {
            return values().find { it.name == value } ?: OTHER
        }

        fun getAllCategories(): List<String> {
            return values().map { it.displayName }
        }
    }
}

