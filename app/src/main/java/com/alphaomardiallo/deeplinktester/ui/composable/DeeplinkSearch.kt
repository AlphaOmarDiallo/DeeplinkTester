package com.alphaomardiallo.deeplinktester.ui.composable

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.alphaomardiallo.deeplinktester.R
import com.alphaomardiallo.deeplinktester.ui.presenter.MainViewModel
import com.alphaomardiallo.deeplinktester.ui.theme.secondaryDarkColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeeplinkSearch(context: Context, viewModel: MainViewModel) {
    var deeplinkToTest by remember { mutableStateOf(TextFieldValue("")) }

    Row(
        Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.margin_medium)),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = deeplinkToTest,
            onValueChange = {
                deeplinkToTest = it

            },
            modifier = Modifier.weight(1f),
            label = { Text(text = stringResource(id = R.string.text_field_deep_link_hint)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri),
            colors = TextFieldDefaults.textFieldColors(
                textColor = MaterialTheme.colorScheme.primary,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                containerColor = secondaryDarkColor,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.onBackground
            ),
            singleLine = true,
            leadingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.ic_link),
                    contentDescription = stringResource(
                        id = R.string.content_description_icon_link
                    ),
                    modifier = Modifier.size(30.dp)
                )
            }
        )
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.margin_small)))
        IconButton(
            onClick = {
                viewModel.openIntent(
                    context = context,
                    uri = deeplinkToTest.text,
                    )
            },
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Icon(
                imageVector = Icons.Filled.PlayArrow,
                contentDescription = stringResource(
                    id = R.string.content_description_icon_play
                )
            )
        }
    }
}