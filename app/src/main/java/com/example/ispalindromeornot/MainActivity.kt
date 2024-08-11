package com.example.ispalindromeornot

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.ispalindromeornot.data.model.User
import com.example.ispalindromeornot.ui.component.UserItem
import com.example.ispalindromeornot.ui.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

//@HiltAndroidApp
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
        composable("thirdScreen") { ThirdScreen() }
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

//@Composable
@Composable
fun ThirdScreen(
    viewModel: UserViewModel = hiltViewModel(),
    onUserSelected: (String) -> Unit
) {
    val users by viewModel.users.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
//    val isRefreshing by viewModel.isRefreshing.collectAsState()



    // Load data when the screen is composed
    LaunchedEffect(Unit) {
        viewModel.loadUsers()
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