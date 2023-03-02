package com.assignment.userregistration.network
import com.assignment.userregistration.model.request.LoginRequest
import com.assignment.userregistration.model.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("api")
    suspend fun getLogin(@Body loginRequest: LoginRequest): LoginResponse
}