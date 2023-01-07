package com.alphaomardiallo.deeplinktester.main.domain

data class UiHistoryDeeplink(
    val id: Int,
    var link: String,
    var timeStamp: Long = 879290L,
)

fun provideFakeListHistory() =
    listOf(
        UiHistoryDeeplink(id = 0, link = "dakar://"),
        UiHistoryDeeplink(id = 1, link = "dakar://"),
        UiHistoryDeeplink(id = 2, link = "dakar://"),
        UiHistoryDeeplink(id = 3, link = "dakar://")
    )