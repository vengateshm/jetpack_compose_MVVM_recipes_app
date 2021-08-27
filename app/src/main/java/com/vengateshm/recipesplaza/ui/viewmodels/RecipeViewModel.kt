package com.vengateshm.recipesplaza.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vengateshm.recipesplaza.model.ApiResult
import com.vengateshm.recipesplaza.model.Recipe
import com.vengateshm.recipesplaza.model.RecipeDetail
import com.vengateshm.recipesplaza.model.ScreenState
import com.vengateshm.recipesplaza.repository.RecipesRepository
import com.vengateshm.recipesplaza.repository.RecipesRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipeViewModel : ViewModel() {
    lateinit var selectedMealCategory: String
        private set
    lateinit var selectedMealId: String
        private set

    private val recipesRepository: RecipesRepository = RecipesRepositoryImpl()

    private var _mealsList = MutableLiveData<ScreenState<List<Recipe>>>()
    val recipeList: LiveData<ScreenState<List<Recipe>>> = _mealsList

    private var _mealsDetail = MutableLiveData<ScreenState<List<RecipeDetail>>>()
    val mealsDetail: LiveData<ScreenState<List<RecipeDetail>>> = _mealsDetail

    fun getMealsByCategory(category: String) {
        _mealsList.value = ScreenState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = recipesRepository.getMealsByCategory(category)) {
                is ApiResult.Success -> {
                    _mealsList.postValue(ScreenState.Success(result.data.recipes))
                }
                is ApiResult.Error -> {
                    _mealsList.postValue(ScreenState.Error(result.exception))
                }
            }
        }
    }

    fun getMealDetailsById(mealId: String) {
        _mealsDetail.value = ScreenState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = recipesRepository.getMealDetail(mealId)) {
                is ApiResult.Success -> {
                    _mealsDetail.postValue(ScreenState.Success(result.data.recipeDetails))
                }
                is ApiResult.Error -> {
                    result.exception.printStackTrace()
                    _mealsDetail.postValue(ScreenState.Error(result.exception))
                }
            }
        }
    }

    fun setSelectedCategory(category: String) {
        selectedMealCategory = category
    }

    fun setSelectedMealId(mealId: String) {
        selectedMealId = mealId
    }
}