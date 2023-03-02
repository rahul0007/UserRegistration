package com.assignment.userregistration.database
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.assignment.userregistration.database.user.UserMaster
import com.assignment.userregistration.database.user.UserMastersDAO

@Database(entities = arrayOf((UserMaster::class)), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userMastersDAO(): UserMastersDAO
    companion object {
        private var dbInstance: AppDatabase? = null

        fun getAppDB(context: Context): AppDatabase {
            if (dbInstance == null) {
                dbInstance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "UserRegistrationDB"
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return dbInstance!!
        }
    }
}