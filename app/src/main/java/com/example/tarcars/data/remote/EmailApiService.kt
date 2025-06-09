package com.example.tarcars.data.remote

import com.example.tarcars.data.model.EmailRequest
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface EmailApiService {
    @Headers("origin: http://localhost")
    @POST("https://api.emailjs.com/api/v1.0/email/send")
    suspend fun sendEmail(@Body request: EmailRequest)
}