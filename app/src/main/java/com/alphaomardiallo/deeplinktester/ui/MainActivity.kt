@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)

package com.alphaomardiallo.deeplinktester.ui

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.FrameMetrics.ANIMATION_DURATION
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alphaomardiallo.deeplinktester.R
import com.alphaomardiallo.deeplinktester.domain.UiHistoryDeeplink
import com.alphaomardiallo.deeplinktester.ui.presenter.MainViewModel
import com.alphaomardiallo.deeplinktester.ui.theme.DeeplinkTesterTheme
import com.alphaomardiallo.deeplinktester.ui.theme.cardCollapsedBackgroundColor
import com.alphaomardiallo.deeplinktester.ui.theme.cardExpandedBackgroundColor
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

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

fun openIntent(context: Context, uri: String?) {
    try {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(uri)
        )
        context.startActivity(intent)
    } catch (exception: Exception) {
        Log.e(TAG, "openIntent: invalid uri")
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
        topBar = { AppTopBar() },
        modifier = Modifier,
        scaffoldState = scaffoldState,
        sheetContent = { MySheetContent() },
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
                .background(color = Color.Black),
            contentAlignment = Alignment.Center
        ) {
            MainContent(paddingValues = it, context = context, viewModel)
        }
    }
}

@Composable
fun AppTopBar() {
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
            containerColor = MaterialTheme.colorScheme.background,
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
            DeeplinkSearch(context = context)
            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.margin_small)))
            DisplayHistoryTitle()
            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.margin_small)))
            HistoryDisplay(viewModel)
        }

    }
}

@Composable
fun DeeplinkSearch(context: Context) {
    var deeplinkToTest by remember { mutableStateOf(TextFieldValue("")) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.margin_medium)),
        shape = MaterialTheme.shapes.large,
        backgroundColor = MaterialTheme.colorScheme.secondary,
        elevation = dimensionResource(id = R.dimen.margin_x_large),
        content = {
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
                        textColor = MaterialTheme.colorScheme.onSecondary,
                        focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                        containerColor = MaterialTheme.colorScheme.secondary,
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
                    onClick = { openIntent(context = context, uri = deeplinkToTest.text) },
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
    )

}

@Composable
fun DisplayHistoryTitle() {
    Text(text = "History")
}

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun HistoryDisplay(viewModel: MainViewModel) {

    val listCards by viewModel.cards.collectAsStateWithLifecycle()
    val revealedCardIds by viewModel.revealedCardIdsList.collectAsStateWithLifecycle()
    Scaffold() {
        LazyColumn(modifier = Modifier.padding(it)) {
            items(listCards, UiHistoryDeeplink::id) { card ->
                Box(Modifier.fillMaxWidth()) {
                    ActionsRow(
                        actionIconSize = ACTION_ITEM_SIZE.dp,
                        onDelete = {},
                        onEdit = {},
                        onFavorite = {}
                    )
                    DraggableCard(
                        card = card,
                        isRevealed = revealedCardIds.contains(card.id),
                        cardHeight = CARD_HEIGHT.dp,
                        cardOffset = CARD_OFFSET.dp(),
                        onExpand = { viewModel.onItemExpanded(card.id) },
                        onCollapse = { viewModel.onItemCollapsed(card.id) },
                    )
                }
            }
        }
    }
}

@Composable
fun HistoryLinkContainer(deeplink: UiHistoryDeeplink) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box() {
            ActionButtonHistoryDisplay(delete = true)
        }
        Box(modifier = Modifier.weight(1f)) {
            LinkDisplayTextField(uriToDisplay = deeplink.link)
        }
        Box() {
            ActionButtonHistoryDisplay(delete = false)
        }
    }
}

@Composable
fun ActionButtonHistoryDisplay(delete: Boolean) {
    IconButton(
        onClick = { /*TODO*/ },
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier
            .padding(horizontal = dimensionResource(id = R.dimen.margin_small))
            .clip(MaterialTheme.shapes.small)
    ) {
        Icon(
            imageVector = if (delete) Icons.Filled.Delete else Icons.Filled.PlayArrow,
            contentDescription = stringResource(
                id = if (delete) R.string.content_description_icon_play else R.string.content_description_icon_play
            )
        )
    }
}

@Composable
fun LinkDisplayTextField(uriToDisplay: String) {

    var deeplinkToDisplay by remember { mutableStateOf(TextFieldValue(uriToDisplay)) }

    OutlinedTextField(
        value = deeplinkToDisplay,
        onValueChange = {
            deeplinkToDisplay = it

        },
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.margin_small))
            .fillMaxWidth(),
        label = { Text(text = stringResource(id = R.string.text_field_deep_link_hint)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri),
        colors = TextFieldDefaults.textFieldColors(
            textColor = MaterialTheme.colorScheme.onSecondary,
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            containerColor = MaterialTheme.colorScheme.secondary,
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
}

@Composable
private fun MySheetContent() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.6f),
        contentAlignment = Alignment.TopCenter,

        ) {
        LazyColumn(
            Modifier
                .background(MaterialTheme.colorScheme.primary)
                .fillMaxHeight()
        ) {
            // the first item that is visible
            item {
                Box(
                    modifier = Modifier
                        .height(56.dp)
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Image(
                            imageVector = Icons.Filled.Star,
                            contentDescription = stringResource(id = R.string.content_description_icon_star)
                        )
                        Text(
                            text = stringResource(id = R.string.favorites),
                            color = MaterialTheme.colorScheme.onPrimary
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

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun DraggableCard(
    card: UiHistoryDeeplink,
    cardHeight: Dp,
    isRevealed: Boolean,
    cardOffset: Float,
    onExpand: () -> Unit,
    onCollapse: () -> Unit,
) {
    val transitionState = remember {
        MutableTransitionState(isRevealed).apply {
            targetState = !isRevealed
        }
    }
    val transition = updateTransition(transitionState, "cardTransition")
    val cardBgColor by transition.animateColor(
        label = "cardBgColorTransition",
        transitionSpec = { tween(durationMillis = ANIMATION_DURATION_CUSTOM) },
        targetValueByState = {
            if (isRevealed) cardExpandedBackgroundColor else cardCollapsedBackgroundColor
        }
    )
    val offsetTransition by transition.animateFloat(
        label = "cardOffsetTransition",
        transitionSpec = { tween(durationMillis = ANIMATION_DURATION_CUSTOM) },
        targetValueByState = { if (isRevealed) cardOffset else 0f },

        )
    val cardElevation by transition.animateDp(
        label = "cardElevation",
        transitionSpec = { tween(durationMillis = ANIMATION_DURATION_CUSTOM) },
        targetValueByState = { if (isRevealed) 40.dp else 2.dp }
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height(cardHeight)
            .offset { IntOffset(offsetTransition.roundToInt(), 0) }
            .pointerInput(Unit) {
                detectHorizontalDragGestures { _, dragAmount ->
                    when {
                        dragAmount >= MIN_DRAG_AMOUNT -> onExpand()
                        dragAmount < -MIN_DRAG_AMOUNT -> onCollapse()
                    }
                }
            },
        backgroundColor = cardBgColor,
        shape = remember {
            RoundedCornerShape(0.dp)
        },
        elevation = cardElevation,
        content = { CardTitle(cardTitle = card.link) }
    )
}

@Composable
fun CardTitle(cardTitle: String) {
    Text(
        text = cardTitle,
        color = Color.Black,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        textAlign = TextAlign.Center,
    )
}

@Composable
fun ActionsRow(
    actionIconSize: Dp,
    onDelete: () -> Unit,
    onEdit: () -> Unit,
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
        IconButton(
            modifier = Modifier.size(actionIconSize),
            onClick = onEdit,
            content = {
                Icon(
                    painter = rememberVectorPainter(image = Icons.Outlined.Edit),
                    tint = Color.Gray,
                    contentDescription = "edit action",
                )
            },
        )
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

fun Float.dp(): Float = this * density + 0.5f

val density: Float
    get() = Resources.getSystem().displayMetrics.density


