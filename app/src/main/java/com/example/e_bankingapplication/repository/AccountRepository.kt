package com.example.e_bankingapplication.repository

import com.example.e_bankingapplication.models.Account
import com.example.e_bankingapplication.models.Transaction
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AccountRepository {

    private val db = FirebaseFirestore.getInstance()

    suspend fun getAccounts(userId: String): List<Account> {
        return db.collection("accounts")
            .whereEqualTo("userId", userId)
            .get()
            .await()
            .toObjects(Account::class.java)
    }

    suspend fun updateAccount(account: Account) {
        db.collection("accounts").document(account.id).set(account).await()
    }

    suspend fun deleteAccount(accountId: String) {
        db.collection("accounts").document(accountId).delete().await()
    }

    suspend fun createTransaction(transaction: Transaction) {
        db.collection("transactions").add(transaction).await()
    }

    suspend fun purchaseAirtime(phoneNumber: String, amount: Int) {
        // This should be updated based on how airtime purchase is handled in your system.
        db.collection("airtime").add(mapOf("phoneNumber" to phoneNumber, "amount" to amount)).await()
    }
}
