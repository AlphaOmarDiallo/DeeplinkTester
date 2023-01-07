package com.alphaomardiallo.deeplinktester.common.domain

data class DeepLink(
    val id: Int,
    var link: String,
    var title: String?,
    var isFavorite: Boolean = false,
    var timeStamp: Long = 7383862L
)

fun provideFakeList() =
    listOf(
        DeepLink(id = 0, link = "dakar://", title = "Dakar Main", isFavorite = true),
        DeepLink(id = 1, link = "dakar://", title = null, isFavorite = false),
        DeepLink(id = 2, link = "dakar://", title = null, isFavorite = false),
        DeepLink(id = 3, link = "dakar://", title = null, isFavorite = false)
    )
