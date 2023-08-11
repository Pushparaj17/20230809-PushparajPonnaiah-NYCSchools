package com.pushparaj.assignment.nycschools.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "School")
data class School(

    @ColumnInfo(name = "dbn")
    val dbn: String,

    @ColumnInfo(name = "school_name")
    var school_name: String?,

    @ColumnInfo(name = "overview_paragraph")
    var overview_paragraph: String?,

    @ColumnInfo(name = "location")
    var location: String?,

    @ColumnInfo(name = "school_email")
    var school_email: String?,

    @ColumnInfo(name = "website")
    var website: String?,

    @ColumnInfo(name = "phone_number")
    var phone_number: String)
{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}