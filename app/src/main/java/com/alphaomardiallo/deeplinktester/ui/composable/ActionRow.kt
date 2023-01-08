package com.alphaomardiallo.deeplinktester.ui.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ActionsRow(
    actionIconSize: Dp,
    onDelete: () -> Unit,
    //onEdit: () -> Unit,
    onFavorite: () -> Unit,
) {
    Row(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        IconButton(
            modifier = Modifier.size(actionIconSize),
            onClick = onDelete,
            content = {
                Icon(
                    painter = rememberVectorPainter(image = Icons.Outlined.Delete),
                    tint = Color.Gray,
                    contentDescription = "delete action",
                )
            }
        )
        /*IconButton(
            modifier = Modifier.size(actionIconSize),
            onClick = onEdit,
            content = {
                Icon(
                    painter = rememberVectorPainter(image = Icons.Outlined.Edit),
                    tint = Color.Gray,
                    contentDescription = "edit action",
                )
            },
        )*/
        IconButton(
            modifier = Modifier.size(actionIconSize),
            onClick = onFavorite,
            content = {
                Icon(
                    painter = rememberVectorPainter(image = Icons.Outlined.Favorite),
                    tint = Color.Red,
                    contentDescription = "Expandable Arrow",
                )
            }
        )
    }
}