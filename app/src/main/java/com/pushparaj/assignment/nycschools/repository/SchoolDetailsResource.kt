package com.pushparaj.assignment.nycschools.repository

import com.pushparaj.assignment.nycschools.model.SchoolDetails

/**
 * Class maintains the state of school details
 */
sealed class SchoolDetailsResource
    object SchoolDetailsResourceLoading : SchoolDetailsResource()
    data class SchoolDetailsResourceData(val data: List<SchoolDetails>): SchoolDetailsResource()
    data class SchoolDetailsResourceError(val error: String): SchoolDetailsResource()
