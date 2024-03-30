package com.example.habit_helper

// ApiService.kt

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("signup")
    suspend fun signUp(@Body request: SignUpRequest): Response<ResponseBody>
}
