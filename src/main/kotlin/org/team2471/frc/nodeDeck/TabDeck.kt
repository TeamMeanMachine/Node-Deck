package org.team2471.frc.nodeDeck

import javafx.scene.control.Tab
import javafx.scene.control.TabPane

object TabDeck: TabPane() {
    val gridSelectionTab = Tab("Grid Selection")
    val settingsTab = Tab("Settings")
    val autoTab = Tab("Auto Options")
    val alternateATab = Tab("Alternate A")
    val longTab = Tab("Long Format")

    init {
        println("TabDeck says hi!")

        TabDeck.setPrefSize(999.0, 999.9)

        tabMinHeight = 50.0
        tabMinWidth = 100.0

        gridSelectionTab.content = CompactFormat
        settingsTab.content = SettingsTab
        autoTab.content = AutoConfig
        alternateATab.content = AlternateA
        longTab.content = FormatLong

        gridSelectionTab.isClosable = false
        settingsTab.isClosable = false
        autoTab.isClosable = false
        alternateATab.isClosable = false
        longTab.isClosable = false

        tabs.add(gridSelectionTab)
        tabs.add(autoTab)
        tabs.add(settingsTab)
        tabs.add(alternateATab)
        tabs.add(longTab)
    }
}