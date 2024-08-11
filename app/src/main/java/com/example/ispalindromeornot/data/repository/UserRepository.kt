package com.example.ispalindromeornot.data.repository

import com.example.ispalindromeornot.data.model.User
import com.example.ispalindromeornot.data.remote.ApiService
import javax.inject.Inject

class UserRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getUsers(page: Int, perPage: Int): List<User> {
        val response = apiService.getUsers(page, perPage)
        return response.data
    }
}
