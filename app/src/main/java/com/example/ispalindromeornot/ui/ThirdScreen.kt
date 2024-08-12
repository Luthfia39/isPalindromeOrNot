package com.example.ispalindromeornot.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.ispalindromeornot.data.model.User
import com.example.ispalindromeornot.viewmodel.UserViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.flow.filter

@Composable
fun ThirdScreen(
    viewModel: UserViewModel = hiltViewModel(),
    onUserSelected: (String) -> Unit
) {
    val users by viewModel.users.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(false)
    val isError by viewModel.isError.observeAsState(false)

    val lazyListState = rememberLazyListState()

    LaunchedEffect(lazyListState) {
        snapshotFlow { lazyListState.isScrollInProgress }
            .filter { !lazyListState.isScrollInProgress }
            .collect {
                val lastVisibleItem = lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                if (lastVisibleItem == users.size - 1) {
                    viewModel.loadUsers()
                }
            }
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = isLoading),
        onRefresh = { viewModel.refreshUsers() }
    ) {
        if (isError) {
            Text("Failed to load data.")
        } else {
            LazyColumn(
                state = lazyListState,
                contentPadding = PaddingValues(16.dp),
            ) {
                items(users) { user ->
                    UserItem(
                        user = user,
                        onClick = { onUserSelected("${user.first_name} ${user.last_name}") })
                }

                if (isLoading) {
//                    item { CircularProgressIndicator(modifier = Modifier.align()) }
                }
            }
        }
    }
}

@Composable
fun UserItem(user: User, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter(user.avatar),
            contentDescription = null,
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = "${user.first_name} ${user.last_name}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(text = user.email, style = MaterialTheme.typography.bodyMedium)
        }
    }
}