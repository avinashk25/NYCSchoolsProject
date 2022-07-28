package com.example.nycschoolsproject.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nycschoolsproject.SingleLiveEvent
import com.example.nycschoolsproject.data.NYCSchoolAPIService
import com.example.nycschoolsproject.model.SchoolItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the list of schools to be displayed.
 * This calls HTTP API through service API which is injected and updates LiveData value being observed in the fragment.
 */

@HiltViewModel
class SchoolListViewModel @Inject constructor(private val service : NYCSchoolAPIService) : ViewModel(), SwipeRefreshLayout.OnRefreshListener {

    companion object {
        private const val TAG = "SchoolListViewModel"
    }

    // List of schools to be displayed on UI
    private val _items = MutableLiveData<List<SchoolItem>>()
    val items : LiveData<List<SchoolItem>> = _items

    // Refresh status update
    private val _canRefresh = SingleLiveEvent<Boolean>()
    val canRefresh : SingleLiveEvent<Boolean> = _canRefresh

    // Selected school item from the list of schools to be updated in UI
    private val _itemSelected = MutableLiveData<String>()
    val itemSelected : LiveData<String> = _itemSelected

    init {
        // load school list
        onRefresh()
    }

    /**
     * In case, there is no data received from the server. request user to refresh and try fetching the dataset again.
     */

    override fun onRefresh() {
        Log.d(TAG,"onRefresh")
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                service.getSchools()
            }.onFailure {
                _items.postValue(emptyList())
            }.onSuccess {
                _items.postValue(it)
            }
            _canRefresh.postValue(true)
        }
    }

    /**
     * Update LiveData value when user selects a particular school
     * @param schoolDBN Unique value for selected school on which school details will be fetched
     */

    fun openSelectedSchoolDetails(schoolDBN : String) {
        Log.d(TAG,"openSelectedSchoolDetails")
        _itemSelected.value = schoolDBN
    }
}