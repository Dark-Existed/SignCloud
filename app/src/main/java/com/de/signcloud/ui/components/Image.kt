package com.de.signcloud.ui.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import com.de.signcloud.api.ServiceCreator
import com.de.signcloud.utils.RequestHeaderInterceptor
import com.google.accompanist.coil.rememberCoilPainter
import okhttp3.OkHttpClient

@Composable
fun LoadNetworkImageWithToken(
    modifier: Modifier = Modifier,
    imageUrl: String
) {
    Image(
        painter = rememberCoilPainter(
            request = imageUrl,
            imageLoader = ImageLoader.Builder(LocalContext.current).okHttpClient {
                ServiceCreator.clientWithToken
            }.build()
        ),
        contentDescription = null,
        modifier = modifier
    )
}