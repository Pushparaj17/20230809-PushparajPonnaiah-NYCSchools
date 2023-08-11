package com.pushparaj.assignment.nycschools.repository

import com.pushparaj.assignment.nycschools.model.School
import okhttp3.ResponseBody

/**
 * Class maintains the state of school list
 */
sealed class SchoolResource
    object SchoolResourceLoading : SchoolResource()
    data class SchoolResourceData(val schools: List<School>): SchoolResource()
    data class SchoolResourceError(val error: String?): SchoolResource()
