package com.example.e_bankingapplication.models

data class Transaction(
    val id: String = "",
    val accountId: String = "",
    val type: String = "", // E.g., "Deposit", "Withdraw", "Airtime Purchase"
    val amount: Double = 0.0,
    val initialBalance: Double = 0.0,
    val finalBalance: Double = 0.0,
    val timestamp: Long = System.currentTimeMillis(),
    val userName: String = "",
    val userNationalId: String = ""
)
