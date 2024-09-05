package com.example.e_bankingapplication.models


data class Account(
    val id: String = "",
    val userId: String = "",
    val accountNumber: String = "",
    val balance: Double = 0.0,
    val accountType: String = "Savings"
)
