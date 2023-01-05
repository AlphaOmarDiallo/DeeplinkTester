package com.alphaomardiallo.deeplinktester

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.alphaomardiallo.deeplinktester.ui.theme.DeeplinkTesterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DeeplinkTesterTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background) {
                    Column (content = {
                        Greeting("Android")
                        Button(this@MainActivity)
                    })
                }
            }
        }
    }
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

fun openIntent(context: Context){
    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("twitter://ran?type=ce&stage=2&reference=stage-2&category=ouioui"))
        context.startActivity(intent)
    } catch (exception: Exception) {
        Log.e(TAG, "openIntent: error", )
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DeeplinkTesterTheme {
        Greeting("Android")
    }
}