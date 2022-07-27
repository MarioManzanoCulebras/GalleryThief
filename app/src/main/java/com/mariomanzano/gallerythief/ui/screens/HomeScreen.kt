package com.mariomanzano.gallerythief.ui.screens

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun HomeScreen(appState: GalleryThiefAppState) {
    Scaffold(scaffoldState = appState.scaffoldState) {
        val url = remember { mutableStateOf("") }
        Column {
            SearchTextField(
                value = TextFieldValue(url.value),
                modifier = Modifier.padding(top = 36.dp),
                onSearch = {
                    url.value = it
                    //Todo: Search with viewModel
                },
                onTextChange ={}
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