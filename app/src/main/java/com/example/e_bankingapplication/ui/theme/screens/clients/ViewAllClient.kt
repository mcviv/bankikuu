package com.example.e_bankingapplication.ui.theme.screens.clients

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.e_bankingapplication.data.ClientViewModel
import com.example.e_bankingapplication.models.Client
import com.example.e_bankingapplication.navigation.ROUTE_UPDATE_CLIENT

@Composable
fun ViewClientsScreen(navController: NavHostController) {
    val context = LocalContext.current
    val clientViewModel = remember { ClientViewModel(navController, context) }

    // State to hold the list of clients
    val clients = remember { mutableStateListOf<Client>() }
    val client = remember { mutableStateListOf(clients) }

    // Fetch clients from the ViewModel
    LaunchedEffect(Unit) {
        clientViewModel.viewClients(client, clients)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "All Clients",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn {
            items(clients) { client ->
                ClientItem(
                    client = client,
                    navController = navController,
                    clientViewModel = clientViewModel
                )
            }
        }
    }
}

@Composable
fun ClientItem(
    client: Client,
    navController: NavHostController,
    clientViewModel: ClientViewModel
) {
    var showFullText by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .animateContentSize(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color.Gray)
    ) {
        Column(modifier = Modifier.padding(15.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(
                    onClick = { clientViewModel.deleteClient(client.id) },
                    colors = ButtonDefaults.textButtonColors(contentColor = Color.Red)
                ) {
                    Text(text = "DELETE", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
                TextButton(
                    onClick = { navController.navigate("${ROUTE_UPDATE_CLIENT}/${client.id}") },
                    colors = ButtonDefaults.textButtonColors(contentColor = Color.Green)
                ) {
                    Text(text = "UPDATE", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                Text(text = "FIRSTNAME", fontWeight = FontWeight.Bold, color = Color.Black)
                Text(text = client.firstname, fontSize = 20.sp, color = Color.White)

                Text(text = "LASTNAME", fontWeight = FontWeight.Bold, color = Color.Black)
                Text(text = client.lastname, fontSize = 20.sp, color = Color.White)

                Text(text = "GENDER", fontWeight = FontWeight.Bold, color = Color.Black)
                Text(text = client.gender, fontSize = 20.sp, color = Color.White)

                Text(text = "AGE", fontWeight = FontWeight.Bold, color = Color.Black)
                Text(text = client.age, fontSize = 20.sp, color = Color.White)

                Text(text = "BIO", fontWeight = FontWeight.Bold, color = Color.Black)
                Text(
                    text = client.bio,
                    color = Color.White,
                    fontSize = 16.sp,
                    maxLines = if (showFullText) Int.MAX_VALUE else 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.clickable { showFullText = !showFullText }
                )
            }
        }
    }
}
