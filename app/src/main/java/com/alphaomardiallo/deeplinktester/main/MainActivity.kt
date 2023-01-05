@file:OptIn(ExperimentalMaterial3Api::class)

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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.alphaomardiallo.deeplinktester.common.theme.DeeplinkTesterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DeeplinkTesterTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background) {
                    Column(content = {
                        MainScreen()
                    })
                }
            }
        }
    }
}

@Composable
fun MainScreen() {

    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier,
        topBar = { AppTopBar() },
        bottomBar = { },
        snackbarHost = { },
        content = { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues))
        }

    )
}


@Composable
fun AppTopBar() {
    TopAppBar(
        title = { Text(text = stringResource(id = com.alphaomardiallo.deeplinktester.R.string.app_name_formatted)) },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary,
            navigationIconContentColor = MaterialTheme.colorScheme.secondary,
            titleContentColor = MaterialTheme.colorScheme.secondary
        ),
        navigationIcon = {
            Image(
                painter = painterResource(id = com.alphaomardiallo.deeplinktester.R.drawable.ic_link),
                contentDescription = null
            )
        }

    )
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Composable
fun Button(context: Context) {
    androidx.compose.material3.Button(onClick = { openIntent(context) }) {

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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DeeplinkTesterTheme {
        Greeting("Android")
    }
}