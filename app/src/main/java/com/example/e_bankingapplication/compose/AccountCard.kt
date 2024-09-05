package com.example.e_bankingapplication.compose
import androidx.compose.material3.Card
import com.example.e_bankingapplication.models.Account
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun AccountCard(account: Account, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Account Name: ${account.accountNumber}", style = MaterialTheme.typography.headlineMedium)
            Text(text = "Balance: $${account.balance}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
