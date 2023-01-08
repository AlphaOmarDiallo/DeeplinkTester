@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)

package com.alphaomardiallo.deeplinktester.ui

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alphaomardiallo.deeplinktester.R
import com.alphaomardiallo.deeplinktester.ui.composable.DeeplinkSearch
import com.alphaomardiallo.deeplinktester.ui.composable.DisplaySectionTitle
import com.alphaomardiallo.deeplinktester.ui.composable.HistoryDisplay
import com.alphaomardiallo.deeplinktester.ui.composable.TopAppBarDLT
import com.alphaomardiallo.deeplinktester.ui.presenter.MainViewModel
import com.alphaomardiallo.deeplinktester.ui.theme.DeeplinkTesterTheme
import com.alphaomardiallo.deeplinktester.ui.theme.primaryColor
import com.alphaomardiallo.deeplinktester.ui.theme.secondaryDarkColor
import dagger.hilt.android.AndroidEntryPoint

const val ANIMATION_DURATION_CUSTOM = 500
const val MIN_DRAG_AMOUNT = 6
const val ACTION_ITEM_SIZE = 56
const val CARD_HEIGHT = 56
const val CARD_OFFSET = 168f // we have 3 icons in a row, so that's 56 * 3

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DeeplinkTesterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(this, mainViewModel)
                }
            }
        }
    }
}

//Composable
@Composable
fun MainScreen(context: Context, viewModel: MainViewModel) {

    val sheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )
    val scope = rememberCoroutineScope()

    BottomSheetScaffold(
        topBar = { TopAppBarDLT() },
        modifier = Modifier,
        scaffoldState = scaffoldState,
        sheetContent = { BottomSheetContent() },
        snackbarHost = { },
        sheetShape = RoundedCornerShape(
            topEnd = dimensionResource(id = R.dimen.margin_large),
            topStart = dimensionResource(id = R.dimen.margin_large)
        ),
        sheetGesturesEnabled = true,
        sheetBackgroundColor = Color.DarkGray,
        sheetElevation = BottomSheetScaffoldDefaults.SheetElevation,
        sheetPeekHeight = BottomSheetScaffoldDefaults.SheetPeekHeight
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            MainContent(paddingValues = it, context = context, viewModel)
        }
    }
}

@Composable
fun MainContent(paddingValues: PaddingValues, context: Context, viewModel: MainViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.margin_small)))
            DeeplinkSearch(context = context, viewModel)
            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.margin_small)))
            DisplaySectionTitle(title = "History")
            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.margin_small)))
            HistoryDisplay(viewModel)
        }
    }
}

@Composable
private fun BottomSheetContent() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.6f)
            .background(MaterialTheme.colorScheme.secondary),
        contentAlignment = Alignment.TopCenter,

        ) {
        LazyColumn(
            Modifier
                .background(secondaryDarkColor)
                .fillMaxHeight()
        ) {
            // the first item that is visible
            item {
                Box(
                    modifier = Modifier
                        .height(56.dp)
                        .fillMaxWidth()
                        .background(color = secondaryDarkColor),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Image(
                            imageVector = Icons.Filled.Star,
                            contentDescription = stringResource(id = R.string.content_description_icon_star),
                            colorFilter = ColorFilter.tint(primaryColor)
                        )
                        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.margin_small)))
                        Text(
                            text = stringResource(id = R.string.favorites),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
            item {
                Text(text = "something")
            }
            item {
                Text(text = "something")
            }
            item {
                Text(text = "something")
            }
            item {
                Text(text = "something")
            }
            item {
                Text(text = "something")
            }
            item {
                Text(text = "something")
            }
        }
    }
}

fun Float.dp(): Float = this * density + 0.5f

val density: Float
    get() = Resources.getSystem().displayMetrics.density


