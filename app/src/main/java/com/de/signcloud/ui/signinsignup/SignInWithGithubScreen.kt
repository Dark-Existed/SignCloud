package com.de.signcloud.ui.signinsignup

import android.annotation.SuppressLint
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
            WebView(url = loadUrl)
        }
    )
}


@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebView(url: String) {
    AndroidView(factory = {
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