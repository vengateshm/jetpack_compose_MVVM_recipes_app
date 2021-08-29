package com.vengateshm.recipesplaza.ui.theme

import android.content.res.AssetManager
import androidx.compose.material.Typography
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.sp
import com.vengateshm.recipesplaza.R

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)

@ExperimentalTextApi
fun fontFamilyFromAsset(assetManager: AssetManager, fontFilePath: String) = FontFamily(
    Font(assetManager, fontFilePath + "_regular.ttf", FontWeight.Normal),
    Font(assetManager, fontFilePath + "_semi_bold.ttf", FontWeight.Medium),
    Font(assetManager, fontFilePath + "_bold.ttf", FontWeight.Bold),
)

@ExperimentalUnitApi
fun customFontTypography(fontFamily: FontFamily) =
    Typography(
        body1 = TextStyle(
            fontFamily = fontFamily,
            fontSize = TextUnit(14f, TextUnitType.Sp)
        ), h1 = TextStyle(
            fontFamily = fontFamily,
            fontSize = TextUnit(18f, TextUnitType.Sp)
        ),
        h2 = TextStyle(
            fontFamily = fontFamily,
            fontSize = TextUnit(16f, TextUnitType.Sp)
        )
    )