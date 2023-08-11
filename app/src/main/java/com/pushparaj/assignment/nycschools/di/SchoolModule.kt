package com.pushparaj.assignment.nycschools.di

import android.content.Context
import androidx.room.Room
import com.pushparaj.assignment.nycschools.api.ApiService
import com.pushparaj.assignment.nycschools.db.SchoolDatabase
import com.pushparaj.assignment.nycschools.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SchoolModule {

    @Provides
    fun provideBaseUrl() = Constants.BASE_URL

    @Provides
    @Singleton
    fun provideRetrofit(BASE_URL: String): ApiService =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)


    @Singleton
    @Provides
    fun provideSchoolDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        SchoolDatabase::class.java,
        "school_db"
    ).allowMainThreadQueries()
        .build()

    @Singleton
    @Provides
    fun provideDAOAccess(schoolDatabase: SchoolDatabase) = schoolDatabase.getDAOAccess()
}