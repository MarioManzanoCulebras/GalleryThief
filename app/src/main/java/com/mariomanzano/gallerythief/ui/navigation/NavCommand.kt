package com.mariomanzano.gallerythief.ui.navigation

import androidx.navigation.navArgument

sealed class NavCommand(
    internal val feature: Feature,
    internal val subRoute: String = "home",
    private val navArgs: List<NavArg> = emptyList()
) {
    class ContentType(feature: Feature) : NavCommand(feature)

    class ContentTypeByString(feature: Feature) :
        NavCommand(
            feature = feature,
            "gallery",
            navArgs = listOf(NavArg.ItemType)
        ) {
        fun createRoute(itemType: String) = "${feature.route}/$subRoute/$itemType"
    }

    val route = run {
        val argValues = navArgs.map { "{${it.key}}" }
        listOf(feature.route)
            .plus(subRoute)
            .plus(argValues)
            .joinToString("/")
    }

    val args = navArgs.map {
        navArgument(it.key) { type = it.navType }
    }

}