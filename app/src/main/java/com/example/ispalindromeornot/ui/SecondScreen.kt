package com.example.ispalindromeornot.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun SecondScreen(navController: NavHostController, userName: String) {
    var selectedUserName by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Your Name: $userName",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Selected User Name: $selectedUserName",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("thirdScreen") }) {
            Text("Choose a User")
        }
    }
}


//fun SecondScreen(navController: NavController) {
//    var selectedUserName by remember { mutableStateOf("") }

//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(text = "Welcome", modifier = Modifier.padding(bottom = 16.dp))
//
//        Text(text = "Name: ${name ?: "Unknown"}", modifier = Modifier.padding(bottom = 16.dp))
//
//        Text(text = "Selected User Name: $selectedUserName", modifier = Modifier.padding(bottom = 16.dp))
//
//        Button(onClick = {
//            navController.navigate("thirdScreen")
//        }) {
//            Text("Choose a User")
//        }
//    }
//
//    LaunchedEffect(navController) {
//        navController.currentBackStackEntryFlow.collect { backStackEntry ->
//            backStackEntry.savedStateHandle.get<String>("selectedUserName")?.let {
//                selectedUserName = it
//            }
//        }
//    }
//}

//@Preview(showBackground = true)
//@Composable
//fun SecondScreenPreview() {
//    SecondScreen(navController = rememberNavController(), name = "John Doe")
//}