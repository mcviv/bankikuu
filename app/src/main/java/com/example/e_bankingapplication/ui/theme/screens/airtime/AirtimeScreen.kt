package com.example.e_bankingapplication.ui.theme.screens.airtime

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.e_bankingapplication.data.AccountViewModel

@Composable
fun AirtimeScreen(navController: NavController, viewModel: AccountViewModel = viewModel()) {
    var phoneNumber by remember { mutableStateOf("") }
    var selectedAmount by remember { mutableStateOf<Int?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var successMessage by remember { mutableStateOf("") }

    val airtimeAmounts = listOf(10, 20, 50, 100, 200) // Example airtime amounts

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Buy Airtime", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(

            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Phone Number") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = phoneNumber.isEmpty()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Select Airtime Amount", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            airtimeAmounts.forEach { amount ->
                Button(
                    onClick = { selectedAmount = amount },
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp),
//                    colors = ButtonDefaults.buttonColors(
//                        background = if (selectedAmount == amount) MaterialTheme.typography.bodyMedium else MaterialTheme.colors.surface
//                    )
                ) {
                    Text("Ksh $amount")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = Color.Cyan)
            Spacer(modifier = Modifier.height(8.dp))
        }

        if (successMessage.isNotEmpty()) {
            Text(text = successMessage, color = Color.Blue)
            Spacer(modifier = Modifier.height(8.dp))
        }

        Button(
            onClick = {
                isLoading = true
                errorMessage = ""
                successMessage = ""

                viewModel.purchaseAirtime(phoneNumber, selectedAmount ?: 0) { success, message ->
                    isLoading = true
                    if (success) {
                        successMessage = "Airtime purchased successfully!"
                    } else {
                        errorMessage = message ?: "Failed to purchase airtime"
                    }
                }
            },
            enabled = phoneNumber.isNotEmpty() && selectedAmount != null,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = Color.Blue)
            } else {
                Text("Purchase Airtime")
            }
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AirtimeScreenPreview(){
    AirtimeScreen(rememberNavController())
}