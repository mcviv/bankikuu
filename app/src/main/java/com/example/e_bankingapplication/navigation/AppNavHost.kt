package com.example.e_bankingapplication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.e_bankingapplication.data.AccountViewModel
import com.example.e_bankingapplication.ui.theme.screens.account.AccountScreen
import com.example.e_bankingapplication.ui.theme.screens.airtime.AirtimeScreen
import com.example.e_bankingapplication.ui.theme.screens.clients.AddClient
import com.example.e_bankingapplication.ui.theme.screens.clients.UpdateClient
import com.example.e_bankingapplication.ui.theme.screens.clients.ViewClientsScreen
import com.example.e_bankingapplication.ui.theme.screens.home.HomeScreen
import com.example.e_bankingapplication.ui.theme.screens.login.Login
import com.example.e_bankingapplication.ui.theme.screens.register.Register
import com.example.e_bankingapplication.ui.theme.screens.transaction.TransactionScreen




@Composable
fun AppNavHost(
    navController: NavHostController =rememberNavController(),
    startDestination: String = ROUTE_REGISTER) {
    val context = LocalContext.current
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(ROUTE_REGISTER){ Register("",navController)}
        composable(ROUTE_HOME){ HomeScreen(navController)}
        composable(ROUTE_LOGIN){ Login(navController)}
        composable(ROUTE_ACCOUNT){ AccountScreen(navController, accountId = "")}
        composable(ROUTE_TRANSACTION){ TransactionScreen()}
        composable(ROUTE_AIRTIME){ AirtimeScreen(navController)}
        composable(ROUTE_ADD_CLIENT){ AddClient(navController)}
        composable(ROUTE_VIEW_CLIENT){ ViewClientsScreen(navController)}
        composable(ROUTE_UPDATE_CLIENT+"/{id}"){
                passedData -> UpdateClient(
            navController,passedData.arguments?.getString("id")!!
        )
    }
}
}



