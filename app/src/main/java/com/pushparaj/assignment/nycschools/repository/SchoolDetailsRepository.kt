package com.pushparaj.assignment.nycschools.repository

import com.pushparaj.assignment.nycschools.api.ApiService
import com.pushparaj.assignment.nycschools.db.DAOAccess
import com.pushparaj.assignment.nycschools.db.SchoolDatabase
import com.pushparaj.assignment.nycschools.model.School
import com.pushparaj.assignment.nycschools.model.SchoolDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class SchoolDetailsRepository @Inject constructor(private val daoAccess:DAOAccess, private val apiService: ApiService) {

    suspend fun getSchoolDetails():SchoolDetailsResource?  {
        return try {
            val schoolDetails: List<SchoolDetails> = daoAccess.getAllSchoolDetails()
            if(schoolDetails.size > 0) {
                return SchoolDetailsResourceData(schoolDetails)
            }
            val response = apiService.getSchoolDetails().execute()
            if (response.isSuccessful) {
                response.body()?.let {
                    withContext(Dispatchers.IO) {
                        for (schoolDetails in it) {
                            insert(schoolDetails)
                        }
                    }
                    SchoolDetailsResourceData(it)
                }
            } else {
                SchoolDetailsResourceError(response.errorBody().toString())
            }
        } catch (e: Exception) {
            SchoolDetailsResourceError("An error occurred: ${e.message}")
        }
    }

    suspend fun insert(schoolDetails: SchoolDetails) {
        daoAccess?.InsertSchoolDetails(schoolDetails)
    }
}