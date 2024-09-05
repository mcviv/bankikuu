package com.example.e_bankingapplication.ui.theme.screens.clients

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.e_bankingapplication.data.ClientViewModel
import com.example.e_bankingapplication.models.Client
import com.example.e_bankingapplication.navigation.ROUTE_VIEW_CLIENT
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


@Composable
fun UpdateClient(navController: NavController, id: String) {


    var firstname by remember { mutableStateOf("") }
    var lastname by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }

    val context = LocalContext.current

    val currentDataRef = FirebaseDatabase.getInstance().getReference()
        .child("Client/$id")

    // Load existing client data
    DisposableEffect(Unit) {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val client = snapshot.getValue(Client::class.java)
                client?.let {
                    firstname = it.firstname
                    lastname = it.lastname
                    gender = it.gender
                    age = it.age
                    bio = it.bio

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            }
        }
        currentDataRef.addValueEventListener(listener)
        onDispose { currentDataRef.removeEventListener(listener) }
    }

    Scaffold(
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Filled.Home, contentDescription = "Home Icon")
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Filled.Settings, contentDescription = "Settings Icon")
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Filled.Email, contentDescription = "Email Icon")
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { /*TODO*/ },
                        containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                    ) {
                        Icon(Icons.Filled.AccountCircle, contentDescription = "Profile Icon")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(10.dp)
                .fillMaxWidth()
                .background(Color.Gray)
        ) {
            Text(
                text = "UPDATE CLIENT",
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .background(Color.Green)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = { navController.navigate(ROUTE_VIEW_CLIENT)}) {
                    Text(text = "ALL CLIENTS")
                }
                TextButton(onClick = {
                    val clientRepository = ClientViewModel(navController, context)

                    clientRepository.updateClient(
                        firstname = firstname,
                        lastname = lastname,
                        gender = gender,
                        age = age,
                        bio = bio,
                        id = id,

                    )
                }) {
                    Text(text = "UPDATE",
                        color = Color.Blue)
                }

            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

            }
            OutlinedTextField(
                modifier = Modifier.wrapContentWidth().align(Alignment.CenterHorizontally),
                label = { Text(text = "Enter First Name") },
                placeholder = { Text(text = "Please Enter First Name") },
                value = firstname,
                onValueChange = { newName -> firstname = newName }
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                modifier = Modifier.wrapContentWidth().align(Alignment.CenterHorizontally),
                label = { Text(text = "Enter Last Name") },
                placeholder = { Text(text = "Please Enter Last Name") },
                value = lastname,
                onValueChange = { newName -> lastname = newName }
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                modifier = Modifier.wrapContentWidth().align(Alignment.CenterHorizontally),
                label = { Text(text = "Enter your Gender") },
                placeholder = { Text(text = "Please Enter your Gender") },
                value = gender,
                onValueChange = { newGender -> gender = newGender }
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                modifier = Modifier.wrapContentWidth().align(Alignment.CenterHorizontally),
                label = { Text(text = "Enter your Age") },
                placeholder = { Text(text = "Please Enter your Age") },
                value = age,
                onValueChange = { newAge -> age = newAge }
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                modifier = Modifier
                    .height(160.dp)
                    .wrapContentWidth()
                    .align(Alignment.CenterHorizontally),
                label = { Text(text = "Enter description") },
                placeholder = { Text(text = "Please Enter brief description") },
                value = bio,
                singleLine = false,
                onValueChange = { newBio -> bio = newBio }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun UpdateClientPreview() {
    UpdateClient(rememberNavController(), id = "")
}
