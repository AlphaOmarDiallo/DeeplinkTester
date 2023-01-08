package com.alphaomardiallo.deeplinktester.ui.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import com.alphaomardiallo.deeplinktester.R

@Composable
fun CardTitle(cardTitle: String) {
    Text(
        text = cardTitle,
        color = Color.Black,
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.margin_large)),
        textAlign = TextAlign.Center,
    )
}