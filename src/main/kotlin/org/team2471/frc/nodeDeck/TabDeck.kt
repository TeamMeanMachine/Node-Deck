package org.team2471.frc.nodeDeck

import javafx.scene.control.Tab
import javafx.scene.control.TabPane

object TabDeck: TabPane() {
    val gridSelectionTab = Tab("Grid Selection")
    val settingsTab = Tab("General Settings")
    val autoTab = Tab("Robot Auto")
    val testTab = Tab("Test")
    val longTab = Tab("Long Format")

    init {
        println("TabDeck says hi!")

        TabDeck.setPrefSize(999.0, 999.9)

        tabMinHeight = 75.0
        tabMinWidth = 100.0

        longTab.content = LongFormat
        gridSelectionTab.content = CompactFormat
        autoTab.content = AutoConfig
        settingsTab.content = SettingsTab
        testTab.content = TestTab

        gridSelectionTab.isClosable = false
        settingsTab.isClosable = false
        autoTab.isClosable = false
        testTab.isClosable = false
        longTab.isClosable = false

        tabs.add(longTab)
        tabs.add(gridSelectionTab)
        tabs.add(autoTab)
//        tabs.add(testTab)
        tabs.add(settingsTab)
    }
}