package com.coding.newsapp.ui.navigation

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object Headline : Route

    @Serializable
    data object Search : Route

    @Serializable
    data object Bookmark : Route

    @Serializable
    data object NewsDetail : Route

    @Serializable
    data object SettingDetail : Route

}