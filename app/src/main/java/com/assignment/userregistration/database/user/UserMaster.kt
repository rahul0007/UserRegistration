package com.assignment.userregistration.database.user

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "UserMaster")
@Parcelize
data class UserMaster(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "FirstName")
    var FirstName: String = "",

    @ColumnInfo(name = "LastName")
    var LastName: String = "",

    @ColumnInfo(name = "PhoneNumber")
    var PhoneNumber: String = "",

    @ColumnInfo(name = "Email")
    var Email: String = "",

    @ColumnInfo(name = "Gender")
    var Gender: String = "",

    @ColumnInfo(name = "Profile")
    var Profile: String = "",

    @ColumnInfo(name = "Password")
    var Password: String = "",

    @ColumnInfo(name = "Address")
    var Address: String = "",

    @ColumnInfo(name = "Landmark")
    var Landmark: String = "",

    @ColumnInfo(name = "City")
    var City: String = "",

    @ColumnInfo(name = "State")
    var State: String = "",

    @ColumnInfo(name = "PinCode")
    var PinCode: String = "",

    @ColumnInfo(name = "Qualification")
    var Qualification: String = "",

    @ColumnInfo(name = "PassingYear")
    var PassingYear: String = "",

    @ColumnInfo(name = "Grade")
    var Grade: String = "",

    @ColumnInfo(name = "Experience")
    var Experience: String = "",

    @ColumnInfo(name = "Designation")
    var Designation: String = "",

    @ColumnInfo(name = "Domain")
    var Domain: String = ""
):Parcelable
