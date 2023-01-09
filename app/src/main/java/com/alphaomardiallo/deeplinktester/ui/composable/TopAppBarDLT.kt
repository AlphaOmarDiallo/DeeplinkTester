package com.alphaomardiallo.deeplinktester.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alphaomardiallo.deeplinktester.R
import com.alphaomardiallo.deeplinktester.ui.theme.secondaryDarkColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarDLT() {
    TopAppBar(
        title = {
            Column {
                Text(
                    text = stringResource(id = R.string.app_name_formatted),
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSecondary
                )
                Text(
                    text = stringResource(id = R.string.subtitle_app_bar),
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSecondary
                )

            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = secondaryDarkColor,
            navigationIconContentColor = MaterialTheme.colorScheme.secondary,
            titleContentColor = MaterialTheme.colorScheme.secondary
        ),
        navigationIcon = {
            Image(
                painter = painterResource(id = R.drawable.deeplink_tester),
                contentDescription = null,
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.margin_large))
                    .clip(CircleShape)
                    .size(50.dp)
            )
        }
    )
}