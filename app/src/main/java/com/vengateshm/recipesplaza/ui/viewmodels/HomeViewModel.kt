package com.vengateshm.recipesplaza.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vengateshm.recipesplaza.model.ApiResult
import com.vengateshm.recipesplaza.model.Category
import com.vengateshm.recipesplaza.model.ScreenState
import com.vengateshm.recipesplaza.repository.CategoryRepository
import com.vengateshm.recipesplaza.repository.CategoryRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val categoryRepository: CategoryRepository = CategoryRepositoryImpl()

    private var _screenState = MutableLiveData<ScreenState<List<Category>>>()
    val screenState: LiveData<ScreenState<List<Category>>> = _screenState

    fun observeMealCategories() {
        _screenState.value = ScreenState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                when (val result = categoryRepository.getAllCategories()) {
                    is ApiResult.Success -> {
                        _screenState.postValue(ScreenState.Success(result.data.categories))
                    }
                    is ApiResult.Error -> {
                        _screenState.postValue(ScreenState.Error(result.exception))
                    }
                }
            } catch (e: Exception) {
                _screenState.postValue(ScreenState.Error(e))
            }
        }
    }
}