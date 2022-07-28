package com.example.nycschoolsproject.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nycschoolsproject.data.NYCSchoolAPIService
import com.example.nycschoolsproject.model.SchoolSATScoreResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SchoolDetailViewModel @Inject constructor(private val service : NYCSchoolAPIService) : ViewModel() {

    companion object {
        private const val TAG = "SchoolDetailViewModel"
    }

    private val _schoolSATScoreResponse = MutableLiveData<SchoolSATScoreResponse>()
    var schoolSATScoreResponse: LiveData<SchoolSATScoreResponse> = _schoolSATScoreResponse

    private val _emptySchoolSATScore = MutableLiveData(false)
    val emptySchoolSATScore: LiveData<Boolean> = _emptySchoolSATScore

    init {
        setDefaultValues()
    }

    /**
     * Update LiveData value after fetching school specific SAT score details and bind it through LiveData
     * @param schoolDBN Unique value for selected school on which school details will be fetched
     */

    fun updateSchoolDetails(dbn : String){
        Log.d(TAG,"updateSchoolDetails")
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                //HTTP API call through service.
                service.getSchoolSATScores(dbn)
            }.onFailure {
                // No SAT score available for this school. Show an error to the user indicating the same.
                _emptySchoolSATScore.postValue(true)
            }.onSuccess {
                //Update school SAT scores based on success response received.
                _schoolSATScoreResponse.postValue(it.firstOrNull())
            }
        }
    }

    /**
     * Set default values to SchoolSATScoreResponse here.
     */

    private fun setDefaultValues() {
        _schoolSATScoreResponse.value = SchoolSATScoreResponse("","","","","","")
    }
}
