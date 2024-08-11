package com.example.ispalindromeornot.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ispalindromeornot.data.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val repository = UserRepository()

    fun loadUsers(page: Int, perPage: Int) {
        viewModelScope.launch {
            val users = repository.getUsers(page, perPage)
            // Update UI with users data
        }
    }
}