package com.de.signcloud.ui.signinsignup

import android.annotation.SuppressLint
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import com.de.signcloud.R
import com.de.signcloud.ui.components.SignCloudTopAppBarWithBack

sealed class SignInWithGithubEvent {
    object NavigateBack : SignInWithGithubEvent()
}

@Composable
fun SignInWithGithub(
    loadUrl: String,
    modifier: Modifier = Modifier,
    handlerUrl: (String) -> Boolean,
    onEvent: (SignInWithGithubEvent) -> Unit,
) {
    Scaffold(
        topBar = {
            SignCloudTopAppBarWithBack(
                topAppBarText = stringResource(id = R.string.sign_in_with_github),
                onBackPressed = { onEvent(SignInWithGithubEvent.NavigateBack) }
            )
        },
        content = {
            WebView(url = loadUrl, handlerUrl = handlerUrl)
        }
    )
}


@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebView(
    url: String,
    handlerUrl: (String) -> Boolean
) {
    AndroidView(factory = {
        WebView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            settings.javaScriptEnabled = true
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
                    return handlerUrl(url)
                }
            }
        }
    }, update = {
        it.loadUrl(url)
    })
}