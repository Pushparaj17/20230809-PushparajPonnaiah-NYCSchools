package com.pushparaj.assignment.nycschools.api

import com.pushparaj.assignment.nycschools.model.School
import com.pushparaj.assignment.nycschools.model.SchoolDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("s3k6-pzi2.json")
    fun getSchools(): retrofit2.Call<List<School>>

    @GET("f9bf-2cp4.json")
    fun getSchoolDetails(): retrofit2.Call<List<SchoolDetails>>
}