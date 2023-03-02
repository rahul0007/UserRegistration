package com.assignment.userregistration.network

import com.assignment.userregistration.model.request.LoginRequest
import com.assignment.userregistration.model.response.LoginResponse
import javax.inject.Inject

class ServiceImp @Inject constructor(private val apiService: ApiService) {
    suspend fun getLogin(loginRequest: LoginRequest): LoginResponse =apiService.getLogin(loginRequest)
}