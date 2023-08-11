package com.pushparaj.assignment.nycschools.repository

import android.content.Context
import com.pushparaj.assignment.nycschools.api.ApiService
import com.pushparaj.assignment.nycschools.db.DAOAccess
import com.pushparaj.assignment.nycschools.db.SchoolDatabase
import com.pushparaj.assignment.nycschools.model.School
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SchoolRepository @Inject constructor(private val daoAccess:DAOAccess, private val apiService: ApiService) {


    suspend fun getSchools(): SchoolResource? {
        return try {
            val schools: List<School> = daoAccess.getAllSchools()
            if(schools.size > 0) {
                return SchoolResourceData(schools)
            }
            val response = apiService.getSchools().execute()
            if (response.isSuccessful) {
                response.body()?.let {
                    withContext(Dispatchers.IO) {
                        for (school in it) {
                            insert(school)
                        }
                    }
                    SchoolResourceData(it)
                }
            } else {
                SchoolResourceError(response.errorBody().toString())
            }
        } catch (e: Exception) {
            SchoolResourceError("An error occurred: ${e.message}")
        }
    }

    suspend fun insert(school: School) {
       daoAccess?.InsertSchool(school)
    }
}