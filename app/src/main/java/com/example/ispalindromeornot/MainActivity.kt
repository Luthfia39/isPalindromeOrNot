package com.example.ispalindromeornot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.ispalindromeornot.ui.ThirdScreen
import com.example.ispalindromeornot.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "firstScreen"
    ) {
        composable("firstScreen") { FirstScreen(navController) }
        composable("secondScreen") { backStackEntry ->
            SecondScreen(
                navController = navController,
                userName = backStackEntry.arguments?.getString("userName") ?: ""
            )
        }
        composable("thirdScreen") { val userViewModel: UserViewModel = hiltViewModel()
            ThirdScreen(viewModel = userViewModel) { selectedUserName ->
                navController.previousBackStackEntry?.savedStateHandle?.set("selectedUserName", selectedUserName)
                navController.popBackStack()
            } }
    }
}

@Composable
fun FirstScreen(navController: NavHostController) {
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var sentence by remember { mutableStateOf(TextFieldValue("")) }
    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Enter your name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = sentence,
            onValueChange = { sentence = it },
            label = { Text("Enter a sentence") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            if (isPalindrome(sentence.text)) {
                dialogMessage = "isPalindrome"
            } else {
                dialogMessage = "not palindrome"
            }
            showDialog = true
        }) {
            Text("Check")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("secondScreen") }) {
            Text("Next")
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Result") },
            text = { Text(dialogMessage) },
            confirmButton = {
                Button(onClick = { showDialog = false }) {
                    Text("OK")
                }
            }
        )
    }
}

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
        Button(onClick = {
            navController.navigate("thirdScreen")
        }) {
            Text("Choose a User")
        }
    }
}

fun isPalindrome(text: String): Boolean {
    val cleanedText = text.replace("\\s".toRegex(), "").lowercase()
    return cleanedText == cleanedText.reversed()
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp()
}