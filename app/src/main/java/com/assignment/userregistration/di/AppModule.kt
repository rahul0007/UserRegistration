package com.assignment.userregistration.di
import android.app.Application
import com.assignment.userregistration.database.AppDatabase
import com.assignment.userregistration.database.user.UserMastersDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun getAppDB(context: Application): AppDatabase {
        return AppDatabase.getAppDB(context)
    }
    @Provides
    @Singleton
    fun getUserMastersDAO(appDb: AppDatabase): UserMastersDAO {
        return appDb.userMastersDAO()
    }

}

