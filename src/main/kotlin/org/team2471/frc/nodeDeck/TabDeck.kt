package org.team2471.frc.nodeDeck

import javafx.scene.control.Tab
import javafx.scene.control.TabPane

object TabDeck: TabPane() {
    val gridSelectionTab = Tab("Grid Selection")
    val settingsTab = Tab("General Settings")
    val autoTab = Tab("Robot Auto")
    val testTab = Tab("Test")
    val longTab = Tab("Long Format")

    val fontSize = "15"

    init {
        println("TabDeck says hi!")

//        TabDeck.setPrefSize(1000.0, 2000.0)

        tabMinHeight = 60.0
        tabMinWidth = 200.0

        longTab.content = LongFormat
        gridSelectionTab.content = CompactFormat
        autoTab.content = AutoConfig
        settingsTab.content = SettingsTab
        testTab.content = TestTab

        gridSelectionTab.style = "-fx-font-size: ${fontSize} px"
        settingsTab.style = "-fx-font-size: ${fontSize} px"
        autoTab.style = "-fx-font-size: ${fontSize} px"
        testTab.style = "-fx-font-size: ${fontSize} px"
        longTab.style = "-fx-font-size: ${fontSize} px"

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