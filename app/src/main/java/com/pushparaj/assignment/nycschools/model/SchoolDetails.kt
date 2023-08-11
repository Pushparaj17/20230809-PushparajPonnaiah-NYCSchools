package com.pushparaj.assignment.nycschools.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="SchoolDetails")
data class SchoolDetails(
    @ColumnInfo(name = "dbn")
    val dbn: String,

    @ColumnInfo(name = "school_name")
    var school_name: String?,

    @ColumnInfo(name = "num_of_sat_test_takers")
    var num_of_sat_test_takers: String?,

    @ColumnInfo(name = "sat_critical_reading_avg_score")
    var sat_critical_reading_avg_score: String?,

    @ColumnInfo(name = "sat_math_avg_score")
    var sat_math_avg_score: String?,

    @ColumnInfo(name = "sat_writing_avg_score")
    var sat_writing_avg_score: String)
{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}