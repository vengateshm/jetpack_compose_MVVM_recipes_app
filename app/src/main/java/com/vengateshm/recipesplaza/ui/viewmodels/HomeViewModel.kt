package com.vengateshm.recipesplaza.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vengateshm.recipesplaza.model.ApiResult
import com.vengateshm.recipesplaza.model.RecipeCategory
import com.vengateshm.recipesplaza.model.ScreenState
import com.vengateshm.recipesplaza.repository.RecipeCategoryRepository
import com.vengateshm.recipesplaza.repository.RecipeCategoryRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val recipeCategoryRepository: RecipeCategoryRepository = RecipeCategoryRepositoryImpl()

    private var _screenState = MutableLiveData<ScreenState<List<RecipeCategory>>>()
    val screenState: LiveData<ScreenState<List<RecipeCategory>>> = _screenState

    fun observeMealCategories() {
        _screenState.value = ScreenState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                when (val result = recipeCategoryRepository.getAllCategories()) {
                    is ApiResult.Success -> {
                        _screenState.postValue(ScreenState.Success(result.data.recipeCategories))
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