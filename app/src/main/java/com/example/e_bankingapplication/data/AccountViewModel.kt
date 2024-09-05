package com.example.e_bankingapplication.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_bankingapplication.models.Account
import com.example.e_bankingapplication.models.Transaction
import com.example.e_bankingapplication.repository.AccountRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AccountViewModel : ViewModel() {

    private val repository = AccountRepository()

    val _accounts = MutableStateFlow<List<Account>>(emptyList())
    val accounts: StateFlow<List<Account>> get() = _accounts

    fun loadAccounts(userId: String) {
        viewModelScope.launch {
            _accounts.value = repository.getAccounts(userId)
        }
    }

    fun updateAccount(account: Account) {
        viewModelScope.launch {
            repository.updateAccount(account)
            loadAccounts(account.userId)
        }
    }

    fun deleteAccount(accountId: String, userId: String) {
        viewModelScope.launch {
            repository.deleteAccount(accountId)
            loadAccounts(userId)
        }
    }

    fun createTransaction(transaction: Transaction) {
        viewModelScope.launch {
            repository.createTransaction(transaction)
        }
    }

    fun purchaseAirtime(phoneNumber: String, amount: Int, onResult: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            try {
                repository.purchaseAirtime(phoneNumber, amount)
                onResult(true, null)
            } catch (e: Exception) {
                onResult(false, e.message )


            }
        }
    }
}
