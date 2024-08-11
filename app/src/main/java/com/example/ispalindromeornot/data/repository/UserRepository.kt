package com.example.ispalindromeornot.data.repository

import com.example.ispalindromeornot.data.network.ApiConfig
import com.example.ispalindromeornot.data.network.ApiService

class UserRepository {
    private val apiService: ApiService = ApiConfig.apiService

    suspend fun getUsers(page: Int, perPage: Int) = apiService.getUsers(page, perPage)
}