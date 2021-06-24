package com.de.signcloud.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.de.signcloud.ui.checkin.CreateCheckInEvent

@Composable
fun Location(
    modifier: Modifier = Modifier,
    locationName: String = "",
    onEvent: (CreateCheckInEvent) -> Unit
) {
    Card(shape = RoundedCornerShape(16.dp)) {
        Row(
            modifier = Modifier.height(25.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Icon(
                    Icons.Outlined.LocationOn,
                    contentDescription = null,
                    modifier.padding(start = 4.dp)
                )
                Text(text = locationName)
                Icon(
                    Icons.Outlined.Refresh,
                    contentDescription = null,
                    modifier
                        .padding(end = 4.dp)
                        .clickable {
                            onEvent(CreateCheckInEvent.RefreshLocation)
                        }
                )
            }
        }
    }
}