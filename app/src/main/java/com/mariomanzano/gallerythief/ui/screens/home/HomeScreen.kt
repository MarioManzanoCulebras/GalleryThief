package com.mariomanzano.gallerythief.ui.screens.home

import android.content.res.Resources
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.web.*
import com.mariomanzano.domain.Error
import com.mariomanzano.gallerythief.R
import com.mariomanzano.gallerythief.ui.GalleryThiefAppState
import com.mariomanzano.gallerythief.ui.navigation.Feature
import com.mariomanzano.gallerythief.ui.navigation.NavCommand
import com.mariomanzano.gallerythief.ui.screens.common.ErrorMessage
import com.mariomanzano.gallerythief.ui.screens.common.ThiefIcon


@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun HomeScreen(appState: GalleryThiefAppState) {
    val url = remember { mutableStateOf("") }
    val enableDownloadButton = remember { mutableStateOf(false) }
    val errorWebView = remember { mutableStateOf("")}

    Scaffold(
        scaffoldState = appState.scaffoldState,
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.width(24.dp))
                    FloatingActionButton(onClick = {
                        appState.navController.navigate(
                            NavCommand.ContentTypeByString(Feature.GALLERY).createRoute(" ")
                        )
                    }) {
                        Image(
                            painter = painterResource(ThiefIcon.ThiefGallery.resourceId),
                            contentDescription = "Gallery"
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    if (enableDownloadButton.value) {
                        FloatingActionButton(onClick = {
                            appState.navController.navigate(
                                NavCommand.ContentTypeByString(Feature.GALLERY)
                                    .createRoute(url.value.ifEmpty { "jsoup.org" })
                            )
                        }) {
                            Image(
                                painter = painterResource(ThiefIcon.ThiefDownload.resourceId),
                                contentDescription = "ThiefImages"
                            )
                        }
                        Spacer(modifier = Modifier.width(24.dp))
                    }
            }
        }) { paddingValues ->

        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            SearchTextField(
                value = TextFieldValue(url.value),
                modifier = Modifier.padding(top = 36.dp),
                onSearch = {
                    errorWebView.value = ""
                    url.value = it
                },
                onTextChange ={}
            )

            WebView(
                modifier = Modifier.weight(1f),
                url = url,
                enableDownloadButton = enableDownloadButton,
                errorWebView = errorWebView)
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
            leadingIcon = { Image(painterResource(
                id = R.drawable.ic_thief_search),
                contentDescription = "SearchIcon") },
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun WebView(modifier: Modifier, url: MutableState<String>, enableDownloadButton : MutableState<Boolean>, errorWebView: MutableState<String>) {
    val keyboardController = LocalSoftwareKeyboardController.current

    val webViewState = rememberWebViewState(url = url.value)
    val navigatorWebView = rememberWebViewNavigator()

    val loadingState = webViewState.loadingState

    if (loadingState is LoadingState.Loading) {
        LinearProgressIndicator(
            progress = loadingState.progress,
            modifier = Modifier.fillMaxWidth()
        )
    }
    if (errorWebView.value.isNotEmpty()){
        ErrorMessage(error = Error.Unknown(errorWebView.value), onClickRetry = {
            errorWebView.value = ""})
    } else {
        val webClient = remember {
            object : AccompanistWebViewClient() {
                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    enableDownloadButton.value = false
                    errorWebView.value = error?.description.toString()
                    super.onReceivedError(view, request, error)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    keyboardController?.hide()
                    enableDownloadButton.value = true
                }
            }
        }

        WebView(
            state = webViewState,
            modifier = modifier.background(colorResource(id = R.color.orangeBackground)),
            navigator = navigatorWebView,
            onCreated = { webView ->
                webView.settings.javaScriptEnabled = true
            },
            client = webClient
        )
    }

}