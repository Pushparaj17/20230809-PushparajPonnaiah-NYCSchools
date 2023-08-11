package com.pushparaj.assignment.nycschools.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pushparaj.assignment.nycschools.model.School
import com.pushparaj.assignment.nycschools.model.SchoolDetails


@Dao
interface DAOAccess {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun InsertSchool(school: School)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun InsertSchoolDetails(school: SchoolDetails)

    @Query("SELECT * FROM School WHERE dbn =:dbn")
    fun getSchool(dbn: String?): School

    @Query("SELECT * FROM SchoolDetails WHERE dbn =:dbn")
    fun getSchoolDetails(dbn: String?): SchoolDetails

    @Query("SELECT * FROM SchoolDetails WHERE id =:id")
    fun getSchoolDetails(id: Int?): SchoolDetails

    @Query("SELECT * FROM School")
    fun getAllSchools(): List<School>

    @Query("SELECT * FROM SchoolDetails")
    fun getAllSchoolDetails(): List<SchoolDetails>
}