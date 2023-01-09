package com.alphaomardiallo.deeplinktester.ui.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.alphaomardiallo.deeplinktester.R

@Composable
fun DisplaySectionTitle(title: String) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.margin_medium))
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_history),
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = "History icon",
        )
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.margin_medium)))
        Text(text = title, color = MaterialTheme.colorScheme.primary, fontSize = 24.sp, fontWeight = FontWeight.Bold)
    }

}