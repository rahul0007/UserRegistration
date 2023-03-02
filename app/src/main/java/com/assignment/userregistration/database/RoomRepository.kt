package com.assignment.userregistration.database

import com.assignment.userregistration.database.user.UserMaster
import com.assignment.userregistration.database.user.UserMastersDAO
import javax.inject.Inject

class RoomRepository @Inject constructor(
    private val userMastersDAO: UserMastersDAO,
    private val appDatabase: AppDatabase
) {
    fun getUserList(): List<UserMaster> {
        return userMastersDAO.getUserList()
    }

    fun clearAllTables() {
        appDatabase.clearAllTables()
    }

    fun insertUser(userMaster: UserMaster) {
        userMastersDAO.insertUser(userMaster)
    }
}