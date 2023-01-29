package org.team2471.frc.nodeDeck

import javafx.scene.control.Tab
import javafx.scene.control.TabPane

object TabDeck: TabPane() {
    val gridSelectionTab = Tab("Grid Selection")
    val settingsTab = Tab("Settings")
    val autoTab = Tab("Auto Options")
    val alternateATab = Tab("Alternate A")

    init {
        println("TabDeck says hi!")

        TabDeck.setPrefSize(999.0, 999.9)

        tabMinHeight = 50.0
        tabMinWidth = 100.0

        gridSelectionTab.content = NodeSelectionTab
        settingsTab.content = SettingsTab
        autoTab.content = AutoConfig
        alternateATab.content = AlternateA

        gridSelectionTab.isClosable = false
        settingsTab.isClosable = false
        autoTab.isClosable = false
        alternateATab.isClosable = false

        tabs.add(gridSelectionTab)
        tabs.add(autoTab)
        tabs.add(settingsTab)
        tabs.add(alternateATab)
    }
}