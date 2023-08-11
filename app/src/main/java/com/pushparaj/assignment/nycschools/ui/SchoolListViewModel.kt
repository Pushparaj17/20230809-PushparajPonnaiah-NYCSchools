package com.pushparaj.assignment.nycschools.ui

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pushparaj.assignment.nycschools.model.School
import com.pushparaj.assignment.nycschools.model.SchoolDetails
import com.pushparaj.assignment.nycschools.repository.SchoolDetailsRepository
import com.pushparaj.assignment.nycschools.repository.SchoolDetailsResource
import com.pushparaj.assignment.nycschools.repository.SchoolRepository
import com.pushparaj.assignment.nycschools.repository.SchoolResource
import com.pushparaj.assignment.nycschools.repository.SchoolResourceLoading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SchoolListViewModel @Inject constructor (private val schoolRepository: SchoolRepository,
                                               private val schoolDetailsRepository: SchoolDetailsRepository): ViewModel() {
    // For School List
    private val _schools = MutableLiveData<SchoolResource>()
    val schools: LiveData<SchoolResource>
        get() = _schools

    // For School Details List
    private val _schoolDetails = MutableLiveData<SchoolDetailsResource>()
    val schoolDetails: LiveData<SchoolDetailsResource>
        get() = _schoolDetails

    init {
        fetchSchoolsInfo()
    }

    private fun fetchSchoolsInfo() = viewModelScope.launch(Dispatchers.IO) {
        _schools.postValue(SchoolResourceLoading)
        val job1 = launch {
            schoolRepository.getSchools()?.let {
                _schools.postValue(it)
            }
        }
        val job2 = launch {
            schoolDetailsRepository.getSchoolDetails().let {
                _schoolDetails.postValue(it)
            }
        }
    }

    suspend fun insertSchools(schools: List<School>) {
        for(school in schools) {
            schoolRepository.insert(school)
        }
    }

    suspend fun insertSchoolDetails(schoolDetails:  List<SchoolDetails>) {
        for(schoolDetail in schoolDetails) {
            schoolDetailsRepository.insert(schoolDetail)
        }
    }

}

