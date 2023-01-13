package com.alphaomardiallo.deeplinktester.ui.composable

import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.alphaomardiallo.deeplinktester.ui.theme.secondaryLightColor

@Composable
fun FabApp() {
    ExtendedFloatingActionButton(
        onClick = { /*TODO*/ },
        backgroundColor = secondaryLightColor,
        text = { Text(text = "Clear", color = MaterialTheme.colorScheme.primary) },
        icon = {
            Icon(imageVector = Icons.Outlined.Delete,
                contentDescription = "Icon delete item",
                tint = MaterialTheme.colorScheme.primary)
        }
    )
}

@Preview
@Composable
fun Preview() {
    FabApp()
}