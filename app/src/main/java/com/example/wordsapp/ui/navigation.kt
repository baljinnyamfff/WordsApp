package com.example.wordsapp.ui.navigation

import com.example.wordsapp.R
import com.lixoten.flightsearch.NavigationDestination

object HomeScreenDestination : NavigationDestination {
    override val route: String = "home"
    override val titleRes: Int = R.string.home_screen_title
}

object AddEditScreenDestination : NavigationDestination {
    override val route: String = "addedit/{wordId}"
    override val titleRes: Int = R.string.edit_screen_title
}

object SettingsScreenDestination : NavigationDestination {
    override val route: String = "settings"
    override val titleRes: Int = R.string.settings_screen_title
}
