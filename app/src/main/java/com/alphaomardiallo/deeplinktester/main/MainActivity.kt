@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)

package com.alphaomardiallo.deeplinktester.main

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alphaomardiallo.deeplinktester.common.theme.DeeplinkTesterTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DeeplinkTesterTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background) {
                    MainHomeScreen()
                }
            }
        }
    }
}

fun openIntent(context: Context) {
    try {
        val intent = Intent(Intent.ACTION_VIEW,
            Uri.parse("twitter://ran?type=ce&stage=2&reference=stage-2&category=ouioui"))
        context.startActivity(intent)
    } catch (exception: Exception) {
        Log.e(TAG, "openIntent: error")
    }

}

//Composables

@Composable
fun MainHomeScreen() {

    val sheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )
    val scope = rememberCoroutineScope()

    BottomSheetScaffold(
        topBar = { AppTopBar() },
        modifier = Modifier,
        scaffoldState = scaffoldState,
        sheetContent = { MySheetContent() },
        snackbarHost = { },
        sheetGesturesEnabled = true,
        sheetBackgroundColor = Color.DarkGray,
        sheetElevation = BottomSheetScaffoldDefaults.SheetElevation,
        sheetPeekHeight = BottomSheetScaffoldDefaults.SheetPeekHeight
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Button(onClick = {
                scope.launch {
                    if (sheetState.isCollapsed) {
                        sheetState.expand()
                    } else {
                        sheetState.collapse()
                    }
                }
            }) {
                Text(text = "Bottom sheet fraction: ${sheetState.progress.fraction}")
            }
        }
    }

}

@Composable
private fun MySheetContent() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Bottom sheet",
            fontSize = 60.sp
        )
    }
}

@Composable
fun AppTopBar() {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = com.alphaomardiallo.deeplinktester.R.string.app_name_formatted),
                fontSize = 16.sp

            )
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background,
            navigationIconContentColor = MaterialTheme.colorScheme.secondary,
            titleContentColor = MaterialTheme.colorScheme.secondary
        ),
        navigationIcon = {
            Image(
                painter = painterResource(id = com.alphaomardiallo.deeplinktester.R.drawable.ic_link),
                contentDescription = null,
                Modifier.padding(horizontal = 16.dp)
            )
        }

    )
}

@Composable
fun Button(context: Context) {
    Button(onClick = { openIntent(context) }) {

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DeeplinkTesterTheme {

    }
}