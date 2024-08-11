package com.example.ispalindromeornot.data.remote

import com.example.ispalindromeornot.data.model.User
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    suspend fun getUsers(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): UserResponse
}

data class UserResponse(
    val data: List<User>
)