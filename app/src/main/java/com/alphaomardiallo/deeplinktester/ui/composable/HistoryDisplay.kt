package com.alphaomardiallo.deeplinktester.ui.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alphaomardiallo.deeplinktester.domain.UiHistoryDeeplink
import com.alphaomardiallo.deeplinktester.ui.*
import com.alphaomardiallo.deeplinktester.ui.presenter.MainViewModel

@OptIn(ExperimentalLifecycleComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HistoryDisplay(viewModel: MainViewModel) {

    val listCards by viewModel.cards.collectAsStateWithLifecycle()
    val revealedCardIds by viewModel.revealedCardIdsList.collectAsStateWithLifecycle()
    Scaffold {
        LazyColumn(modifier = Modifier.padding(it)) {
            items(listCards, UiHistoryDeeplink::id) { card ->
                Box(Modifier.fillMaxWidth()) {
                    ActionsRow(
                        actionIconSize = ACTION_ITEM_SIZE.dp,
                        onDelete = {},
                        onFavorite = {},
                        isFavorite = card.isFavorite
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