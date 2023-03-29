package com.firas.smartmeals.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.firas.smartmeals.data.model.Category
import com.firas.smartmeals.data.repository.Repository
import com.firas.smartmeals.others.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.migration.AliasOf
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class MealViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _mealsStateFlow = MutableStateFlow<Resource<List<Category>>>(Resource.Loading())
    val mealsStateFlow = _mealsStateFlow.asStateFlow()





    private var searchJob: Job? = null


    fun fetchMeals(word: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L)
            repository.getListMeals(word).onEach {
                _mealsStateFlow.value = it
            }.launchIn(viewModelScope)
        }


    }




}