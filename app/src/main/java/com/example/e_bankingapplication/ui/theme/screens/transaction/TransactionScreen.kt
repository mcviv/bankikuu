package com.example.e_bankingapplication.ui.theme.screens.transaction

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.e_bankingapplication.data.AccountViewModel
import com.example.e_bankingapplication.models.Account
import com.example.e_bankingapplication.models.Transaction
import com.example.e_bankingapplication.ui.theme.MyAppTheme


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun TransactionScreen(viewModel: AccountViewModel = remember { AccountViewModel() }) {
    val context = LocalContext.current
    val accountId = "some_account_id" // Set a valid account ID for testing
    val account = viewModel.accounts.value.find { it.id == accountId }

    LaunchedEffect(accountId) {
        viewModel.loadAccounts("userId") // Pass actual userId if needed
    }

    Column(modifier = Modifier.padding(16.dp)) {
        account?.let {
            Text(text = "Account Number: ${it.accountNumber}")
            Text(text = "Current Balance: $${it.balance}")

            Button(
                onClick = {
                    val initialBalance = it.balance
                    val transactionAmount = 100.0
                    val finalBalance = initialBalance - transactionAmount
                    val transaction = Transaction(
                        id = "trans_id_1",
                        accountId = it.id,
                        type = "Withdraw",
                        amount = transactionAmount,
                        initialBalance = initialBalance,
                        finalBalance = finalBalance,
                        timestamp = System.currentTimeMillis(),
                        userName = "John Doe",
                        userNationalId = "123456789"
                    )

                    viewModel.createTransaction(transaction)
                    viewModel.updateAccount(it.copy(balance = finalBalance))
                }
            ) {
                Text("Make Transaction")
            }
        } ?: run {
            Text(text = "Account not found")
        }
    }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun TransactionPreview() {
    // Provide a mock AccountViewModel and mock data
    val mockAccountViewModel = remember {
        AccountViewModel().apply {

            _accounts.value = listOf(
                Account(
                    id = "some_account_id",
                    accountNumber = "123456789",
                    balance = 500.0,
                    userId = "userId"
                )
            )
        }
    }

    MyAppTheme {
        TransactionScreen(viewModel = mockAccountViewModel)
    }
}
