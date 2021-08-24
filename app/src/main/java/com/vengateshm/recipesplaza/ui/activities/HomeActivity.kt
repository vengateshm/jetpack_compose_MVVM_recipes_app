package com.vengateshm.recipesplaza.ui.activities

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.vengateshm.recipesplaza.R
import com.vengateshm.recipesplaza.model.Category
import com.vengateshm.recipesplaza.model.ScreenState
import com.vengateshm.recipesplaza.ui.viewmodels.HomeViewModel

class HomeActivity : ComponentActivity() {

    private val viewModel: HomeViewModel by viewModels()

    @ExperimentalUnitApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.observeMealCategories()
        setContent {
            val screenState = viewModel.screenState.observeAsState()
            screenState.value?.let {
                CategoriesScreen(screenState = it)
            }
        }
    }
}

@ExperimentalUnitApi
@Composable
fun CategoriesScreen(screenState: ScreenState<List<Category>>) {
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
                CategoriesList(categories = screenState.data)
            }
            is ScreenState.Error -> {

            }
        }
    }
}

@ExperimentalUnitApi
@Composable
fun CategoriesList(categories: List<Category>) {
    LazyColumn {
        items(categories) { category ->
            CategoryItem(category = category)
        }
    }
}

@ExperimentalUnitApi
@Composable
fun CategoryItem(category: Category) {
    Card(
        modifier = Modifier.padding(8.dp),
    ) {
        Column. {
            CategoryThumbnail(thumbnailUrl = category.strCategoryThumb)
            Text(
                modifier = Modifier.padding(8.dp),
                text = category.strCategory,
                style = TextStyle(color = Color(0xFF333333),
                    fontSize = TextUnit(20f, TextUnitType.Sp)))
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

@SuppressLint("UnrememberedMutableState")
@Composable
fun loadPicture(url: String, @DrawableRes defaultImage: Int): MutableState<Bitmap?> {
    val bitmapState: MutableState<Bitmap?> = mutableStateOf(null)

    // Load placeholder image
    Glide.with(LocalContext.current)
        .asBitmap()
        .load(defaultImage)
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                bitmapState.value = resource
            }

            override fun onLoadCleared(placeholder: Drawable?) {

            }
        })

    // Load original image from network
    Glide.with(LocalContext.current)
        .asBitmap()
        .load(url)
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                bitmapState.value = resource
            }

            override fun onLoadCleared(placeholder: Drawable?) {

            }
        })
    return bitmapState
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