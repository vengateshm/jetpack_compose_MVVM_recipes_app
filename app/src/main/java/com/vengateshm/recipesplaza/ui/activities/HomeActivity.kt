package com.vengateshm.recipesplaza.ui.activities

import android.content.Intent
import android.content.res.AssetManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.vengateshm.recipesplaza.R
import com.vengateshm.recipesplaza.model.RecipeCategory
import com.vengateshm.recipesplaza.model.ScreenState
import com.vengateshm.recipesplaza.ui.composeViews.loadPicture
import com.vengateshm.recipesplaza.ui.theme.CustomFontTheme
import com.vengateshm.recipesplaza.ui.viewmodels.HomeViewModel
import com.vengateshm.recipesplaza.utils.KEY_RECIPE_CATEGORY_ID

class HomeActivity : ComponentActivity() {

    private val viewModel: HomeViewModel by viewModels()

    @ExperimentalTextApi
    @ExperimentalUnitApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.observeMealCategories()
        setContent {
            assets?.let {
                CustomFontTheme(assetManager = it, fontFilePath = "fonts/opensans") {
                    val screenState = viewModel.screenState.observeAsState()
                    screenState.value?.let {
                        CategoriesScreen(screenState = it) { category ->
                            Intent(this@HomeActivity, RecipesActivity::class.java)
                                .apply {
                                    putExtra(KEY_RECIPE_CATEGORY_ID, category.strCategory)
                                }
                                .also {
                                    startActivity(it)
                                }
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalUnitApi
@Composable
fun CategoriesScreen(
    screenState: ScreenState<List<RecipeCategory>>,
    onCategoryClicked: (RecipeCategory) -> Unit,
) {
    Box(modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth()
        .fillMaxHeight(),
        contentAlignment = Alignment.Center) {
        when (screenState) {
            is ScreenState.Loading -> {
                CircularProgressIndicator()
            }
            is ScreenState.Success -> {
                CategoriesList(recipeCategories = screenState.data, onCategoryClicked)
            }
            is ScreenState.Error -> {

            }
        }
    }
}

@ExperimentalUnitApi
@Composable
fun CategoriesList(
    recipeCategories: List<RecipeCategory>,
    onCategoryClicked: (RecipeCategory) -> Unit,
) {
    LazyColumn {
        items(recipeCategories) { category ->
            CategoryItem(recipeCategory = category, onCategoryClicked)
        }
    }
}

@ExperimentalUnitApi
@Composable
fun CategoryItem(recipeCategory: RecipeCategory, onCategoryClicked: (RecipeCategory) -> Unit) {
    Card(modifier = Modifier.padding(8.dp)) {
        Column(modifier = Modifier.clickable(onClick = {
            onCategoryClicked(recipeCategory)
        })) {
            CategoryThumbnail(thumbnailUrl = recipeCategory.strCategoryThumb)
            Text(
                modifier = Modifier.padding(8.dp),
                text = recipeCategory.strCategory,
                style = MaterialTheme.typography.h1)
            /*Spacer(modifier = Modifier.height(4.dp))
            Text(text = category.strCategoryDescription,
                style = TextStyle(color = Color(0xFF777777),
                    fontSize = TextUnit(14f, TextUnitType.Sp)))*/
        }
    }
}

@Composable
fun CategoryThumbnail(thumbnailUrl: String) {
    loadPicture(url = thumbnailUrl, defaultImage = R.drawable.thumbnail_bg).value?.apply {
        Image(bitmap = this.asImageBitmap(), contentDescription = "",
            modifier = Modifier
                .defaultMinSize(minHeight = 150.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop)
    }
}

// With state hoisting
@Composable
fun HelloScreen() {
    var name by rememberSaveable { mutableStateOf("") }
    HelloContent(name = name, onNameChange = { name = it })
}

@Composable
fun HelloContent(name: String, onNameChange: (String) -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        // remember retain the name value across recomposition
        // and does not retain during configuration change
        //var name by remember { mutableStateOf("NA") }

        // name value retained during configuration change


        // name value initializes to default during every recomposition
        //var name by mutableStateOf("")

        if (name.isNotEmpty()) {
            Text(text = "Hello, $name!",
                modifier = Modifier.padding(bottom = 8.dp),
                style = MaterialTheme.typography.h5)
        }
        OutlinedTextField(value = name,
            onValueChange = { onNameChange(it) },
            label = { Text(text = "Name") })
    }
}

/*********************************************************************************************/

// Without state hoisting
@Composable
fun HelloContent() {
    Column(modifier = Modifier.padding(16.dp)) {
        // remember retain the name value across recomposition
        // and does not retain during configuration change
        //var name by remember { mutableStateOf("NA") }

        // name value retained during configuration change
        var name by rememberSaveable { mutableStateOf("") }

        // name value initializes to default during every recomposition
        //var name by mutableStateOf("")

        if (name.isNotEmpty()) {
            Text(text = "Hello, $name!",
                modifier = Modifier.padding(bottom = 8.dp),
                style = MaterialTheme.typography.h5)
        }
        OutlinedTextField(value = name,
            onValueChange = { name = it },
            label = { Text(text = "Name") })
    }
}