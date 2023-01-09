package com.alphaomardiallo.deeplinktester.domain

data class UiHistoryDeeplink(
    val id: Int,
    var link: String,
    var title: String?,
    var isFavorite: Boolean = false,
    var timeStamp: Long = 7383862L
)

fun provideFakeListHistory() =
    listOf(
        UiHistoryDeeplink(
            id = 0,
            link = "dakar://",
            title = null,
            isFavorite = true,
            timeStamp = 0
        ),
        UiHistoryDeeplink(
            id = 1,
            link = "dakar://",
            title = null,
            isFavorite = false,
            timeStamp = 0
        ),
        UiHistoryDeeplink(
            id = 2,
            link = "dakar://",
            title = null,
            isFavorite = false,
            timeStamp = 0
        ),
        UiHistoryDeeplink(
            id = 3,
            link = "dakar://",
            title = null,
            isFavorite = false,
            timeStamp = 0
        ),
        UiHistoryDeeplink(
            id = 4,
            link = "dakar://",
            title = null,
            isFavorite = true,
            timeStamp = 0
        ),
        UiHistoryDeeplink(
            id = 6,
            link = "dakar://",
            title = null,
            isFavorite = true,
            timeStamp = 0
        ),
        UiHistoryDeeplink(
            id = 7,
            link = "dakar://",
            title = null,
            isFavorite = true,
            timeStamp = 0
        ),
        UiHistoryDeeplink(
            id = 8,
            link = "dakar://",
            title = null,
            isFavorite = true,
            timeStamp = 0
        ),
        UiHistoryDeeplink(
            id = 9,
            link = "dakar://",
            title = null,
            isFavorite = true,
            timeStamp = 0
        ),
        UiHistoryDeeplink(
            id = 10,
            link = "dakar://",
            title = null,
            isFavorite = true,
            timeStamp = 0
        ),
        UiHistoryDeeplink(
            id = 5,
            link = "dakar://",
            title = null,
            isFavorite = true,
            timeStamp = 0
        )
    )