package com.example.e_bankingapplication.ui.theme.screens.account



import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.e_bankingapplication.data.AccountViewModel
import com.example.e_bankingapplication.models.Transaction
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AccountScreen(navController: NavController, accountId: String) {
    val context = LocalContext.current
    val viewModel: AccountViewModel = viewModel()
    val accounts by viewModel.accounts.collectAsState()

    val account = remember { mutableStateOf(accounts.find { it.id == accountId }) }

    var amount by remember { mutableStateOf("") }
    var transactionType by remember { mutableStateOf("Deposit") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Account Details") })
        },
        content = {
            account.value?.let { account ->
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Account Number: ${account.accountNumber}",
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Text(
                        text = "Current Balance: $${account.balance}",
                        style = MaterialTheme.typography.headlineMedium
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(text = "Transaction Amount:")
                    BasicTextField(
                        value = amount,
                        onValueChange = { amount = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = "Transaction Type:")
                    Row {
                        RadioButton(
                            selected = transactionType == "Deposit",
                            onClick = { transactionType = "Deposit" }
                        )
                        Text("Deposit")
                        Spacer(modifier = Modifier.width(8.dp))
                        RadioButton(
                            selected = transactionType == "Withdraw",
                            onClick = { transactionType = "Withdraw" }
                        )
                        Text("Withdraw")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            val transactionAmount = amount.toDoubleOrNull() ?: 0.0
                            if (transactionAmount > 0) {
                                val initialBalance = account.balance // Corrected reference to initial balance
                                val finalBalance = if (transactionType == "Deposit") {
                                    initialBalance + transactionAmount
                                } else {
                                    initialBalance - transactionAmount
                                }

                                val transaction = Transaction(
                                    id = UUID.randomUUID().toString(),
                                    accountId = account.id,
                                    type = transactionType,
                                    amount = transactionAmount,
                                    initialBalance = initialBalance,
                                    finalBalance = finalBalance,
                                    timestamp = System.currentTimeMillis(),
                                    userName = "User Name", // Replace with actual user name from your user state
                                    userNationalId = "User National ID" // Replace with actual user National ID
                                )
                                viewModel.createTransaction(transaction)
                                viewModel.updateAccount(account.copy(balance = finalBalance))
                                amount = ""
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Submit Transaction")
                    }
                }
            } ?: run {
                Text(text = "Account not found", modifier = Modifier.padding(16.dp))
            }
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AccountScreenPreview() {
    AccountScreen(rememberNavController(), "account_id_example")}
