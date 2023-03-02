package com.assignment.userregistration.repository

import com.assignment.userregistration.model.request.LoginRequest
import com.assignment.userregistration.network.ApiService
import javax.inject.Inject


class Repository @Inject constructor(private val apiService: ApiService) {
    //    flow task on background
    suspend fun getLogin(loginRequest : LoginRequest)=apiService.getLogin(loginRequest)
}