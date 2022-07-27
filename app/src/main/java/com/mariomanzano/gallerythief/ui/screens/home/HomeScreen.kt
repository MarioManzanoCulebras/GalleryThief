package com.mariomanzano.gallerythief.ui.screens.home

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.accompanist.pager.ExperimentalPagerApi
import com.mariomanzano.gallerythief.ui.GalleryThiefAppState
import com.mariomanzano.gallerythief.ui.navigation.Feature
import com.mariomanzano.gallerythief.ui.navigation.NavCommand
import com.mariomanzano.gallerythief.ui.navigation.navigatePoppingUpToStartDestination
import com.mariomanzano.gallerythief.ui.screens.common.ThiefIcon

@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun HomeScreen(appState: GalleryThiefAppState) {
    val url = remember { mutableStateOf("") }

    Scaffold(
        scaffoldState = appState.scaffoldState,
        floatingActionButton = {
            Row {
                Spacer(modifier = Modifier.weight(1f))
                FloatingActionButton(onClick = {
                    appState.navController.navigatePoppingUpToStartDestination(
                    NavCommand.ContentTypeByString(Feature.GALLERY)
                        .createRoute(url.value.ifEmpty { "jsoup.org" })
                )}) {
                    Image(
                        painter = painterResource(ThiefIcon.ThiefGallery.resourceId),
                        contentDescription = "Gallery",
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
            }
        }) {

        Column {
            SearchTextField(
                value = TextFieldValue(url.value),
                modifier = Modifier.padding(top = 36.dp),
                onSearch = {
                    url.value = it
                },
                onTextChange ={
                    url.value = it.text
                }
            )

            WebView(
                modifier = Modifier.weight(1f),
                url = url.value)
        }
    }
}

@Composable
fun SearchTextField(value: TextFieldValue,
                    modifier: Modifier,
                    onTextChange: (TextFieldValue) -> Unit,
                    onSearch: (String) -> Unit) {
    var text by remember { mutableStateOf(value) }

    Surface( modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(50),
        elevation = 12.dp) {

        TextField(
            value = text,
            onValueChange = {
                text = it
                if (it.text.isNotEmpty()) {
                    onTextChange(text)
                }
            },
            leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "TrailingIcon") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            placeholder = { Text("Write a valid url...") },
            textStyle = TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = Color.Black
            ),
            keyboardActions = KeyboardActions(onSearch = { onSearch(text.text) })
        )
    }
}

@Composable
fun WebView(modifier: Modifier, url: String) {
    AndroidView(
        modifier = modifier,
        factory = {
        WebView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webViewClient = WebViewClient()
            loadUrl(url)
        }
    }, update = {
        it.loadUrl(url)
    })

}