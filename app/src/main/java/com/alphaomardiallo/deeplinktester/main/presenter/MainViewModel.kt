package com.alphaomardiallo.deeplinktester.main.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alphaomardiallo.deeplinktester.main.domain.UiHistoryDeeplink
import com.alphaomardiallo.deeplinktester.main.domain.provideFakeListHistory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _cards = MutableStateFlow(listOf<UiHistoryDeeplink>())
    val cards: StateFlow<List<UiHistoryDeeplink>> get() = _cards

    private val _revealedCardIdsList = MutableStateFlow(listOf<Int>())
    val revealedCardIdsList: StateFlow<List<Int>> get() = _revealedCardIdsList

    init {
        getHistoryDeepLinksList()
    }

    private fun getHistoryDeepLinksList() {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                val uiHistoryDeepLinkList = provideFakeListHistory()
                _cards.emit(uiHistoryDeepLinkList)
            }
        }
    }

    fun onItemExpanded(deepLinkID: Int) {
        // Check if deepLinkID is already in the list
        if (_revealedCardIdsList.value.contains(deepLinkID)) return

        // Create a new mutable list, add deepLinkID to it, and update _revealedCardIdsList
        _revealedCardIdsList.value = _revealedCardIdsList.value.toMutableList().also { list ->
            list.add(deepLinkID)
        }
    }

    fun onItemCollapsed(deepLinkID: Int) {
        // Check if deepLinkID is in the list
        if (!_revealedCardIdsList.value.contains(deepLinkID)) return

        // Create a new mutable list, remove deepLinkID from it, and update _revealedCardIdsList
        _revealedCardIdsList.value = _revealedCardIdsList.value.toMutableList().also { list ->
            list.remove(deepLinkID)
        }
    }


}