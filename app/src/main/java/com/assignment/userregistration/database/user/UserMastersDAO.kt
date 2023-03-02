package com.assignment.userregistration.database.user

import androidx.room.*

@Dao
interface UserMastersDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(userMaster: UserMaster)

    @Query("select * from UserMaster")
    fun getUserList(): List<UserMaster>

}