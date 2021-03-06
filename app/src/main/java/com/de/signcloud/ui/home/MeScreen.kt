package com.de.signcloud.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.de.signcloud.R
import com.de.signcloud.repository.remote.UserRepository
import com.de.signcloud.ui.components.LoadNetworkImageWithToken
import com.de.signcloud.ui.components.SignCloudTopAppBar
import com.de.signcloud.ui.theme.SignCloudTheme


@Composable
fun Me(
    modifier: Modifier = Modifier,
    onEvent: (HomeEvent) -> Unit
) {
    Scaffold(
        topBar = {
            SignCloudTopAppBar(topAppBarText = stringResource(id = R.string.me))
        },
        content = {
            MeContent(modifier = modifier, onEvent = onEvent)
        }
    )
}


@Composable
fun MeContent(
    modifier: Modifier = Modifier,
    onEvent: (HomeEvent) -> Unit
) {
    Column(modifier.padding(16.dp, 0.dp)) {
        Spacer(modifier = modifier.height(20.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            LoadNetworkImageWithToken(
                modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .clickable {  },
                imageUrl = UserRepository.user.avatar
            )
            Spacer(modifier = modifier.width(40.dp))
            Text(text = UserRepository.user.name, fontSize = 20.sp)
        }
        Spacer(modifier = modifier.height(32.dp))
        Text(
            modifier = modifier
                .fillMaxWidth()
                .clickable {
                    onEvent(HomeEvent.NavigateToChangeRole)
                },
            text = stringResource(id = R.string.change_role),
            fontSize = 20.sp
        )
        Spacer(modifier = modifier.height(32.dp))
        Text(
            modifier = modifier
                .fillMaxWidth()
                .clickable {
                    onEvent(HomeEvent.NavigateSetUserInfo)
                },
            text = stringResource(id = R.string.set_user_info),
            fontSize = 20.sp
        )
        Spacer(modifier = modifier.height(32.dp))
        TextButton(
            onClick = { onEvent(HomeEvent.SignOut) },
            modifier = modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.sign_out),
                color = SignCloudTheme.colors.error,
                fontSize = 18.sp
            )
        }
    }
}